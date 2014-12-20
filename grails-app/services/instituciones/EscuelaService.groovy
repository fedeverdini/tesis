package instituciones

import personal.alumno.Alumno;
import utils.exception.*
import utils.RedisService

class EscuelaService {
	
	RedisService redisService

	static transactional = false

	def saveEscuela(data) {
		if(redisService.get("escuela_${data.numero_ins}") || Escuela.findByNumeroIns(data.numero_ins)) {
			throw new ConflictException("La escuela ya existe")
		}

		Escuela e = new Escuela()

		e.nombre = data.nombre
		e.calle = data.direccion.calle
		e.numero = data.direccion.numero
		e.telefono = data.telefono
		e.numeroIns = data.numero_ins
		e.seccion = data.seccion

		if(!e.validate()){
			throw new BadRequestException("No se pudo crear la escuela: $data. Datos no v�lidos")
		}
		
		try{
			redisService.set("escuela_${data.numero_ins}", e.ToJSON().toString())
			return e.save(flush:true)
		} catch (Exception ex){
			log.error(ex)
			return null
		}
	}
	
	Escuela getEscuela(String numeroIns) {
		return Escuela.findByNumeroIns(numeroIns)
	}
	
	def updateEscuela(data){

		Escuela actual = Escuela.findByNumeroIns(data.numero_ins)
		
		if(!actual) {
			throw new Exception("La escuela no existe")
		}
		
		if(!actual.numeroIns.equals(data.numero_ins)){
			throw new NoModificationException("No se puede modificar el número de la institución")
		}

		actual.nombre = data.nombre
		actual.calle = data.direccion.calle
		actual.numero = data.direccion.numero
		actual.telefono = data.telefono
		actual.numeroIns = data.numero_ins
		actual.seccion = data.seccion

		if(!actual.validate()){
			throw new NoModificationException("No se pudo actualizar info para la escuela: $data.numero_ins. Datos no v�lidos")
		}

		return actual.save(flush:true)
	}
}
