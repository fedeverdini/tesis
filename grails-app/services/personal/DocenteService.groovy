package personal

import personal.docente.*;
import utils.RedisService;
import utils.exception.APIException;
import utils.exception.BadRequestException
import utils.exception.ConflictException;
import utils.exception.NoModificationException;
import utils.exception.NotFoundException
import utils.parser.Parser

class DocenteService {

	RedisService redisService

	Docente saveDocente(data) {

		if(redisService.get("docente_${data.dni}") || Docente.findByDni(data.dni)) {
			throw new ConflictException("El docente ya existe")
		}

		Docente d = new Docente()

		d.dni = data.dni
		d.nombre = data.nombre
		d.apellido = data.apellido
		d.calle = data.direccion.calle
		d.numero = data.direccion.numero
		d.piso = data.direccion.piso
		d.depto = data.direccion.depto
		d.telefono = data.telefono
		d.nacimiento = Parser.parseStringToDate(data.nacimiento)
		
		if(!d.validate()){
			throw new BadRequestException("No se pudo crear el docente con DNI: $data.dni. Datos no v�lidos")
		}

		redisService.set("docente_${data.dni}", data.dni.toString())
		return d.save(flush:true)
	}

	Docente getDatosPersonales(Long dni) {
		return Docente.findByDni(dni)
	}

	Docente updateDocente(data) {

		Docente d = Docente.findByDni(data.dni)
		if(!d) {
			throw new NotFoundException("El docente con DNI: $data.dni no existe")
		}
		
		d.dni = data.dni
		d.nombre = data.nombre
		d.apellido = data.apellido
		d.calle = data.direccion.calle
		d.numero = data.direccion.numero
		d.piso = data.direccion.piso
		d.depto = data.direccion.depto
		d.telefono = data.telefono
		d.nacimiento = Parser.parseStringToDate(data.nacimiento)

		if(!d.validate()){
			throw new NoModificationException("No se pudo actualizar info para el docente con DNI: $data.dni. Datos no v�lidos")
		}

		return d.save(flush:true)
	}

	DocenteE asignarEscuela(data) {

		if(!Docente.findByDni(data.dni)) {
			throw new NotFoundException("El docente con DNI: $data.dni no existe")
		}

		List<DocenteE> escuelas = getEscuelas(data.dni)
		
		DocenteE actual = new DocenteE()

		actual.dni = data.dni
		actual.esc = data.esc
		actual.attr1 = data.attr1
		actual.attr2 = data.attr2
		actual.nivel = data.nivel
		actual.cargo = data.cargo
		actual.actual = true

		boolean exists = false
		escuelas.each { e ->
			exists = actual.equals(e) ?: exists 
		}
		
		if(exists){
			throw new NoModificationException("El docente con DNI: $data.dni ya tiene asignada la escuela $data.esc")
		}
		
		if(!actual.validate()){
			throw new NoModificationException("No se pudo asignar escuela al docente con DNI: $data.dni. Datos no v�lidos")
		}

		return actual.save(flush:true)
	}

	List<DocenteE> getEscuelas(dni){
		return DocenteE.findAllByDni(dni)
	}
}
