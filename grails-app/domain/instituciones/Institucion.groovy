package instituciones

import java.util.Map;

import org.codehaus.groovy.grails.web.json.JSONObject;

class Institucion {
	
	String numeroIns
	String nombre
	String calle
	String numero
	Long telefono
	
	Map ToMap() {
		Map map = [
			"numero_ins": numeroIns,
			"nombre": nombre,
			"calle": calle,
			"numero": numero,
			"telefono": telefono,
		]
		return map
	}
	
	JSONObject ToJSON() {
		return new JSONObject(ToMap())
	}

	static constraints = {
		telefono(nullable:true) 
	}
	
	static mapping = {
		id generator: 'assigned', name:'numeroIns', column:'NUMERO_INS'
	}
}
