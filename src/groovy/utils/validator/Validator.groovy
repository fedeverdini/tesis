package utils.validator

import java.util.Date;
import org.codehaus.groovy.grails.web.json.JSONObject;
import utils.exception.BadRequestException
import instituciones.Escuela
import utils.nivel.Nivel
import utils.parser.Parser
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class Validator {

	def static checkType(value, className) {
		if(!value) {
			throw new BadRequestException("Null value")
		}

		if(className.equals("String")) {
			if(value instanceof String){
				return value
			} else {
				throw new BadRequestException("Invalid param [${value}], must be String")
			}
		}

		if(className.equals("Long")) {
			try {
				return Long.valueOf(value)
			} catch (Exception e) {
				throw new BadRequestException("Invalid param [${value}], must be Long")
			}
		}

		if(className.equals("String-Date")) {
			if(value instanceof String || value.size() == 10){
				try{
					Parser.parseStringToDate(value)
					return value
				} catch (Exception e){
					throw new BadRequestException("Invalid param [${value}], must be 'dd-MM-yyyy'")
				}
			} else {
				throw new BadRequestException("Invalid param [${value}], must be 'dd-MM-yyyy'")
			}
		}
	}

	static def validateAlumnoData(JSONObject data){
		if(!data){
			throw new BadRequestException("Datos insuficientes")
		}

		if(!data.containsKey("dni")) {
			throw new BadRequestException("no se encontró el campo dni")
		} else{
			if(checkType(data.dni, "Long").toString().size() != 8) {
				throw new BadRequestException("el dni debe tener 8 dígitos")
			}
		}
		
		if(!data.containsKey("nombre") || !checkType(data.nombre, "String")) {
			throw new BadRequestException("no se encontró el campo nombre")
		}
		
		if(!data.containsKey("apellido") || !checkType(data.apellido, "String")) {
			throw new BadRequestException("no se encontró el campo apellido")
		}
		
		if(!data.containsKey("direccion")) {
			throw new BadRequestException("no se encontró el campo direccion")
		}
		
		if(!data.direccion.containsKey("calle") || !checkType(data.direccion.calle, "String")) {
			throw new BadRequestException("no se encontró el campo calle")
		}
		
		if(data.direccion.containsKey("numero")) {
			if(!"s/n".equals(data.direccion.numero)) {
				// Lo paso a string por si viene con valor 0
				checkType(data.direccion.numero.toString(), "Long")
			}
		} else {
			throw new BadRequestException("no se encontró el campo numero")
		}
	
		if(!data.direccion.containsKey("piso")) {  
			throw new BadRequestException("no se encontró el campo piso")
		} else {
			// Lo paso a string por si viene con valor 0
			checkType(data.direccion.piso.toString(), "Long")
		}
		
		if(!data.direccion.containsKey("depto")) { 
			throw new BadRequestException("no se encontró el campo depto")
		} else {
			// Lo paso a string por si viene con valor 0
			checkType(data.direccion.depto.toString(), "String")
		}

		if(!data.containsKey("telefono")) {
			throw new BadRequestException("no se encontró el campo telefono")
		} else {
			// Lo paso a string por si viene con valor 0
			checkType(data.telefono.toString(), "Long")
		}
		
		if(!data.containsKey("nacimiento") || !checkType(data.nacimiento, "String-Date")) {
			throw new BadRequestException("no se encontró el campo nacimiento")
		}
	}
	
	static def validateDocenteData(JSONObject data){
		if(!data){
			throw new BadRequestException("Datos insuficientes")
		}

		if(!data.containsKey("dni")) {
			throw new BadRequestException("no se encontró el campo dni")
		} else{
			if(checkType(data.dni, "Long").toString().size() != 8) {
				throw new BadRequestException("el campo dni debe tener 8 dígitos")
			}
		}
		
		if(!data.containsKey("nombre") || !checkType(data.nombre, "String")) {
			throw new BadRequestException("no se encontró el campo nombre")
		}
		
		if(!data.containsKey("apellido") || !checkType(data.apellido, "String")) {
			throw new BadRequestException("no se encontró el campo apellido")
		}
		
		if(!data.containsKey("direccion")) {
			throw new BadRequestException("no se encontró el campo direccion")
		}
		
		if(!data.direccion.containsKey("calle") || !checkType(data.direccion.calle, "String")) {
			throw new BadRequestException("no se encontró el campo calle")
		}
		
		if(data.direccion.containsKey("numero")) {
			if(!"s/n".equals(data.direccion.numero)) {
				// Lo paso a string por si viene con valor 0
				checkType(data.direccion.numero.toString(), "Long")
			}
		} else {
			throw new BadRequestException("no se encontró el campo numero")
		}
	
		if(!data.direccion.containsKey("piso")) {
			throw new BadRequestException("no se encontró el campo piso")
		} else {
			// Lo paso a string por si viene con valor 0
			checkType(data.direccion.piso.toString(), "Long")
		}
		
		if(!data.direccion.containsKey("depto")) {
			throw new BadRequestException("no se encontró el campo depto")
		} else {
			// Lo paso a string por si viene con valor 0
			checkType(data.direccion.depto.toString(), "String")
		}

		if(!data.containsKey("telefono")) {
			throw new BadRequestException("no se encontró el campo telefono")
		} else {
			// Lo paso a string por si viene con valor 0
			checkType(data.telefono.toString(), "Long")
		}
		
		if(!data.containsKey("nacimiento") || !checkType(data.nacimiento, "String-Date")) {
			throw new BadRequestException("no se encontró el campo nacimiento")
		}
	}

	static def validateEscuelaOperationData(data){

//		def validAttr1 = ConfigurationHolder.config.get("attr1")
//		def validAttr2 = ConfigurationHolder.config.get("attr2")
		def validCargos = ConfigurationHolder.config.get("cargos")

		if(!data){
			throw new BadRequestException("Datos insuficientes")
		}

		if(!data.containsKey("dni")) {
			throw new BadRequestException("no se encontró el campo dni")
		} else{
			if(checkType(data.dni, "Long").toString().size() != 8) {
				throw new BadRequestException("el campo dni debe tener 8 dígitos")
			}
		}
		
		if(!data.containsKey("esc")) {
			throw new BadRequestException("no se encontró el campo esc")
		} else {
			if(!Escuela.findByNumeroIns(checkType(data.esc, "String"))){
				throw new BadRequestException("Esc: ${data.esc} no existe")
			}
		}
		
		if(!data.containsKey("attr1") || !(data.attr1 instanceof String)) {
			throw new BadRequestException("no se encontró el campo attr1")
		}
		
		if(!data.containsKey("attr2") || !(data.attr2 instanceof String)) {
			throw new BadRequestException("no se encontró el campo attr2")
		}
		
		if(!data.containsKey("nivel") || !(data.nivel instanceof String) || !(Nivel.getById(data.nivel))) {
			throw new BadRequestException("no se encontró el campo nivel")
		}
		
		if(data.containsKey("cargo")) {
			if(!(data.cargo instanceof String) || !validCargos.contains(data.cargo)) {
				throw new BadRequestException("no se encontró el campo cargo")
			}
		}
	}
	
	static def validateEscuelaData(JSONObject data){
		if(!data){
			throw new BadRequestException("Datos insuficientes")
		}

		if(!data.containsKey("nombre") || !checkType(data.nombre, "String")) {
			throw new BadRequestException("no se encontró el campo nombre")
		}
		
		if(!data.containsKey("direccion")) {
			throw new BadRequestException("no se encontró el campo direccion")
		}
		
		if(!data.direccion.containsKey("calle") || !checkType(data.direccion.calle, "String")) {
			throw new BadRequestException("no se encontró el campo calle")
		}
		
		if(data.direccion.containsKey("numero")) {
			if(!"s/n".equals(data.direccion.numero)) {
				// Lo paso a string por si viene con valor 0
				checkType(data.direccion.numero.toString(), "Long")
			}
		} else {
			throw new BadRequestException("no se encontró el campo numero")
		}
	
		if(!data.containsKey("telefono")) {
			throw new BadRequestException("no se encontró el campo telefono")
		} else {
			// Lo paso a string por si viene con valor 0
			checkType(data.telefono.toString(), "Long")
		}
		
		// TODO: hacer validador para n�mero de escuelas
		if(!data.containsKey("numero_ins") || !checkType(data.numero_ins, "String")) { 
			throw new BadRequestException("no se encontró el campo numero_ins")
		}
		
		// TODO: investigar si hay secciones inv�lidas
		if(!data.containsKey("seccion") || !checkType(data.seccion, "Long")) {
			throw new BadRequestException("no se encontró el campo seccion")
		}
	}
	
	static def validateInasistencias(data){
		
				if(!data){
					throw new BadRequestException("Datos insuficientes")
				}
		
				if(!data.containsKey("dni")) {
					throw new BadRequestException("dni not found")
				} else{
					if(checkType(data.dni, "Long").toString().size() != 8) {
						throw new BadRequestException("invalid dni size")
					}
				}
				
				if(!data.containsKey("attr1") || !(data.attr1 instanceof String)) {
					throw new BadRequestException("attr1 not found")
				}
				
				if(!data.containsKey("nivel") || !(data.nivel instanceof String) || !(Nivel.getById(data.nivel))) {
					throw new BadRequestException("nivel not found")
				}
				
				if(!data.containsKey("inasistencias") || !checkType(data.inasistencias, "Long")) {
					throw new BadRequestException("inasistencias not found")
				}
			}
	
	static def validateLegajoData(data){
		
				if(!data){
					throw new BadRequestException("Datos insuficientes")
				}
		
				if(!data.containsKey("dni")) {
					throw new BadRequestException("no se encontró el campo dni")
				} else{
					if(checkType(data.dni, "Long").toString().size() != 8) {
						throw new BadRequestException("el campo dni debe tener 8 dígitos")
					}
				}
				
				if(!data.containsKey("attr1") || !(data.attr1 instanceof String)) {
					throw new BadRequestException("no se encontró el campo attr1")
				}
				
				// TODO: hacer validador para n�mero de escuelas
				if(!data.containsKey("numero_ins") || !checkType(data.numero_ins, "String")) {
					throw new BadRequestException("no se encontró el campo numero_ins")
				}
				
				if(!data.containsKey("nivel") || !(data.nivel instanceof String) || !(Nivel.getById(data.nivel))) {
					throw new BadRequestException("no se encontró el campo nivel")
				}
				
				if(!data.containsKey("fecha") || !checkType(data.fecha, "String-Date")) {
					throw new BadRequestException("no se encontró el campo fecha")
				}
				
				if(!data.containsKey("observaciones") || !checkType(data.observaciones, "String")) {
					throw new BadRequestException("no se encontró el campo observaciones")
				}
			}
}