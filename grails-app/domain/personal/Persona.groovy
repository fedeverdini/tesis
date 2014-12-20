package personal

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject;
import utils.parser.Parser

abstract class Persona {
	
	Long dni
	String nombre
	String apellido
	String calle
	String numero
	int piso
	String depto
	Long telefono
	Date nacimiento

    static constraints = {
    }
	
	static mapping = {
		id generator: 'assigned', name:'dni', column:'DNI'
	}
	
	Map ToMap() {
		
		Parser parser
		
		Map map = [
			"nombre": nombre,
			"apellido": apellido,
			"dni": dni,
			"direccion": [
				"calle": calle,
				"numero": numero,
				"piso": piso,
				"depto": depto
			],
			"telefono": telefono,
			"nacimiento": Parser.parseToShortDate(nacimiento)
		]
		return map
	}
	
	JSONObject ToJSON() {
		return ToMap() as JSON
	}
}
