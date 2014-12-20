package personal

import personal.alumno.*;
import utils.RedisService;
import utils.exception.APIException;
import utils.exception.BadRequestException
import utils.exception.ConflictException;
import utils.exception.NoModificationException;
import utils.exception.NotFoundException
import utils.parser.Parser

class AlumnoService {

	RedisService redisService

	Alumno saveAlumno(data) {

		if(redisService.get("alumno_${data.dni}") || Alumno.findByDni(data.dni)) {
			throw new ConflictException("El alumno ya existe")
		}

		Alumno a = new Alumno()

		a.dni = data.dni
		a.nombre = data.nombre
		a.apellido = data.apellido
		a.calle = data.direccion.calle
		a.numero = data.direccion.numero
		a.piso = data.direccion.piso
		a.depto = data.direccion.depto
		a.telefono = data.telefono
		a.nacimiento = Parser.parseStringToDate(data.nacimiento)

		if(!a.validate()){
			throw new BadRequestException("No se pudo crear el alumno con DNI: $data.dni. Datos no v�lidos")
		}

		redisService.set("alumno_${data.dni}", data.dni.toString())
		redisService.delete("legajo_${data.dni}")
		return a.save(flush:true)
	}

	Alumno getDatosPersonales(Long dni) {
		return Alumno.findByDni(dni)
	}

	Alumno updateAlumno(data) {

		Alumno a = Alumno.findByDni(data.dni)
		if(!a) {
			throw new NotFoundException("El alumno con DNI: $data.dni no existe")
		}

		a.dni = data.dni
		a.nombre = data.nombre
		a.apellido = data.apellido
		a.calle = data.direccion.calle
		a.numero = data.direccion.numero
		a.piso = data.direccion.piso
		a.depto = data.direccion.depto
		a.telefono = data.telefono
		a.nacimiento = Parser.parseStringToDate(data.nacimiento)

		if(!a.validate()){
			throw new NoModificationException("No se pudo actualizar info para el alumno con DNI: $data.dni. Datos no v�lidos")
		}
		redisService.delete("legajo_${data.dni}")
		return a.save(flush:true)
	}

	AlumnoE asignarEscuela(data) {

		if(!Alumno.findByDni(data.dni)) {
			throw new NotFoundException("El alumno con DNI: $data.dni no existe")
		}

		if(getEscuelaActual(data.dni)) {
			throw new NoModificationException("El alumno con DNI: $data.dni ya tiene asignada una escuela")
		}

		AlumnoE actual = new AlumnoE()

		actual.dni = data.dni
		actual.esc = data.esc
		actual.attr1 = data.attr1
		actual.attr2 = data.attr2
		actual.nivel = data.nivel
		actual.actual = true

		if(!actual.validate()){
			throw new NoModificationException("No se pudo asignar escuela al alumno con DNI: $data.dni. Datos no v�lidos")
		}
		redisService.delete("legajo_${data.dni}")
		redisService.delete("escuela_${data.dni}")
		return actual.save(flush:true)
	}

	List<AlumnoE> getEscuelas(dni){
		return AlumnoE.findAllByDni(dni)
	}

	AlumnoE getEscuelaActual(dni){

		List<AlumnoE> escuelas = AlumnoE.findAllByDni(dni)

		if(escuelas.size() == 0){
			return null
		}

		AlumnoE actual = null

		for(AlumnoE e : escuelas){
			actual = e.actual ? e : actual
		}

		return actual
	}

	AlumnoE cambiarEscuela(data) {

		if(!Alumno.findByDni(data.dni)) {
			throw new NotFoundException("El alumno con DNI: $data.dni no existe")
		}

		AlumnoE origen = getEscuelaActual(data.dni)

		if(!origen){
			throw new NoModificationException("El alumno con DNI: $data.dni no tiene asignada una escuela de origen")
		}

		AlumnoE destino = new AlumnoE()

		destino.dni = data.dni
		destino.esc = data.esc
		destino.attr1 = data.attr1
		destino.attr2 = data.attr2
		destino.nivel = data.nivel
		destino.actual = true

		if(!destino.validate()){
			throw new NoModificationException("No se pudo realizar el cambio del alumno con DNI: $data.dni. Datos no v�lidos")
		}

		destino = destino.save(flush:true)

		if(destino) {
			origen.actual = false
			origen.save(flush:true)
		} else {
			throw new NoModificationException("Error al guardar cambio de escuela para el alumno con DNI: $dni")
		}
		redisService.delete("legajo_${data.dni}")
		redisService.delete("escuela_${data.dni}")
		
		return destino
	}

	Inasistencia inasistencias(data) {

		if(!Alumno.findByDni(data.dni)) {
			throw new NotFoundException("El alumno con DNI: $data.dni no existe")
		}

		if(!getEscuelaActual(data.dni)){
			throw new BadRequestException("El alumno con DNI : $data.dni no tiene asignada ninguna escuela")
		}

		List<Inasistencia> i = Inasistencia.findAllByDni(data.dni)

		Inasistencia actual = new Inasistencia()

		actual.dni = data.dni
		actual.attr1 = data.attr1
		actual.nivel = data.nivel
		actual.inasistencias = data.inasistencias

		boolean exists = false
		i.each {
			exists = actual.equals(it) ?: exists
		}

		if(exists){
			throw new NoModificationException("El alumno con DNI: $data.dni ya tiene asignadas inasistencias para los datos [$data]")
		}
		if(!actual.validate()){
			throw new NoModificationException("No se pudo realizar la carga de inasistencias para el alumno con DNI: $data.dni. Datos no v�lidos")
		}

		return actual.save(flush:true)
	}
}
