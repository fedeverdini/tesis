package legajo

import grails.converters.JSON;

import org.codehaus.groovy.grails.web.json.JSONArray;
import org.codehaus.groovy.grails.web.json.JSONObject;

import personal.AlumnoService;
import personal.alumno.Alumno;
import personal.alumno.AlumnoE;
import personal.alumno.Inasistencia;
import utils.RedisService;
import utils.parser.Parser;
import utils.exception.*;

class LegajoService {

	static transactional = true

	AlumnoService alumnoService
	RedisService redisService

	Legajo addLegajoInfo(data) {
		if(!Alumno.findByDni(data.dni)) {
			throw new NotFoundException("El alumno con DNI: $data.dni no existe")
		}
		
		def escActual = alumnoService.getEscuelaActual(data.dni)

		if(!escActual || escActual.esc != data.numero_ins){
			throw new BadRequestException("El alumno con DNI : $data.dni no tiene asignada la escuela $data.numero_ins")
		}

		List<Legajo> l = Legajo.findAllByDniAndAttr1(data.dni, data.attr1)

		Legajo actual = new Legajo()

		actual.dni = data.dni
		actual.attr1 = data.attr1
		actual.nivel = data.nivel
		actual.numeroIns = data.numero_ins
		actual.fecha = Parser.parseStringToDate(data.fecha)
		actual.observaciones = data.observaciones

		boolean exists = false
		l.each {
			exists = actual.equals(it) ?: exists
		}

		if(exists){
			throw new NoModificationException("El alumno con DNI: $data.dni ya tiene asociados los datos que se intentan cargar")
		}
		if(!actual.validate()){
			throw new NoModificationException("No se pudo realizar la carga del legajo para el alumno con DNI: $data.dni. Datos no v�lidos")
		}

		redisService.delete("legajo_${data.dni}")
		redisService.delete("legajo_${data.dni}_${data.nivel}")
		return actual.save(flush:true)
	}
	
	def getLegajoInfo(dni) {
		
		JSONObject legajo
		def legajoRedis = redisService.get("legajo_${dni}")
		
		if(!legajoRedis){
			def alumno = redisService.get("alumno_${dni}") ?: getJSONAlumno(alumnoService.getDatosPersonales(dni)) 
			
			if(!alumno){
				throw new NotFoundException("El alumno con DNI: $dni no existe")
			} 

			def esc = redisService.get("escuela_${dni}") ?: getJSONEscuela(alumnoService.getEscuelaActual(dni))

			def ni = getLegajoData(dni,"NI")
			def np = getLegajoData(dni,"NP")
			def ns = getLegajoData(dni,"NS")

			legajo = legajoBuilder(dni,alumno,esc,ni,np,ns)

			redisService.set("legajo_${dni}", legajo.toString())
		} else {
			legajo = new JSONObject(legajoRedis)
		}
		return legajo
	}
	
	def getJSONAlumno(alumno){
		return alumno ? new JSONObject(alumno.toMap()) : null
	}
	
	def getJSONEscuela(AlumnoE escuela){
		if(escuela){
			def datosEsc = new JSONObject()
			
			datosEsc.put("nivel_actual", escuela.nivel)
			datosEsc.put("esc_actual", escuela.esc)
			datosEsc.put("curso_actual", escuela.attr1)
			
			redisService.set("escuela_${escuela.dni}", datosEsc.toString())
			
			return datosEsc
		}
		return null
	}
	
	def getLegajoData(dni, nivel){
		
		def legajo = redisService.get("legajo_${dni}_${nivel}")
		
		if(legajo){
			return [nivel, legajo]
		}
		
		def c = Legajo.createCriteria()

		List<Legajo> l = c.list {
			and{
				eq("dni",dni)
				eq("nivel",nivel)
			}
			order("fecha","asc")
		}

		return [nivel,l]
	}
	
	JSONObject legajoBuilder(dni,datos,escuela,ni,np,ns){
		if(!datos || !escuela){
			throw new Exception("Hubo un error al intentar obtener la información del legajo")
		}
		
		JSONObject resp = new JSONObject()
		
		resp.put("alumno",  datos)
		
		resp.put("escolaridad_actual", escuela)
				
		[ni,np,ns].each { legajo ->
			JSONObject datosNivel = new JSONObject()
			
			if(legajo[1] instanceof List){
				legajo[1].each { data ->
					if(!datosNivel.containsKey(data.attr1)){
						datosNivel.put(data.attr1, new JSONArray())
					}
					
					JSONObject json = new JSONObject(["fecha":Parser.parseToShortDate(data.fecha),"escuela":data.numeroIns,"evento":data.observaciones])
					datosNivel.get(data.attr1).put(json)
				}
			} else {
				datosNivel = new JSONObject(legajo[1]) 
			}
			
			switch(legajo[0]){
				case "NI" : resp.put("nivel_inicial", datosNivel ?: null); break;
				case "NP" : resp.put("nivel_primario", datosNivel ?: null); break;
				case "NS" : resp.put("nivel_secundario", datosNivel ?: null); break;
			}
			
			if(datosNivel && !"{}".equals(datosNivel)){
				redisService.set("legajo_${dni}_${legajo[0]}", datosNivel.toString())
			}
		}
		
		return resp
	}
}
