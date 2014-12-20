package instituciones

import java.util.Map;

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject;
import utils.parser.Parser

class Escuela extends Institucion {
	
	int seccion
	
	@Override
	Map ToMap() {
		Map map = [
			"numero_ins": numeroIns,
			"nombre": nombre,
			"calle": calle,
			"numero": numero,
			"telefono": telefono,
			"seccion": seccion
		]
		return map
	}
	
	@Override
	JSONObject ToJSON() {
		return new JSONObject(ToMap())
	}
	
    static constraints = {
		id generator: 'assigned', name:'numero_ins', column:'NUMERO_INS'
    }
	
}
