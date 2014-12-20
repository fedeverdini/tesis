/**
 * @author fverdini
 */

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject
import personal.alumno.Alumno
import instituciones.Escuela
import utils.parser.Parser

class EscuelaFunctionalTests extends functionaltestplugin.FunctionalTestCase {

	static final URL_BASE = "http://localhost:8080/tesis/escuelas"

	void testSaveEscuelaOk() {
		Map json = [
			"nombre": "Luis F. Leloir",
			"numero_ins": "4-063",
			"direccion": [
				"calle": "Jensen",
				"numero": "1000",
			],
			"telefono": 2604499885,
			"seccion": 3
		]

		post("http://localhost:8080/tesis/escuelas/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)

	}
	
	void testSaveEscuelaDuplicated() {
		Map json = [
			"nombre": "Luis F. Leloir",
			"numero_ins": "4-063",
			"direccion": [
				"calle": "Jensen",
				"numero": "1000",
			],
			"telefono": 2604499885,
			"seccion": 3
		]

		post("http://localhost:8080/tesis/escuelas/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 409

	}
	
	void testUpdateEscuelaOk(){
		
		Map json = [
			"nombre": "Francisco P Aura",
			"numero_ins": "1-060",
			"direccion": [
				"calle": "Rawson",
				"numero": "3001",
			],
			"telefono": 2604998877,
			"seccion": 1
		]

		post("http://localhost:8080/tesis/escuelas/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)

		json = [
			"nombre": "Francisco P Aura",
			"numero_ins": "1-060",
			"direccion": [
				"calle": "Rawson",
				"numero": "3001",
			],
			"telefono": 2604234567,
			"seccion": 1
		]

		put("http://localhost:8080/tesis/escuelas/${json.numero_ins}", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 200

		ValidatePostAndPut(json)
	}
		
	void ValidatePostAndPut(json){
		get("http://localhost:8080/tesis/escuelas/${json.numero_ins}", 
			{headers['Accept'] = 'application/json'}
		)

		assertStatus 200

		JSONObject e = new JSONObject(response.getContentAsString()).response
		
		assert e
		assert e.nombre == json.nombre
		assert e.calle == json.direccion.calle
		assert e.numero == json.direccion.numero
		assert e.telefono == json.telefono
		assert e.numero_ins == json.numero_ins
		assert e.seccion == json.seccion
	}

}