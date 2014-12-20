/**
 * @author fverdini
 */

import grails.converters.JSON;

import org.codehaus.groovy.grails.web.json.JSONArray;
import org.codehaus.groovy.grails.web.json.JSONObject
import personal.docente.DocenteE
import utils.parser.Parser

class DocenteFunctionalTests extends functionaltestplugin.FunctionalTestCase {

	static final URL_BASE = "http://localhost:8080/"

	void testSaveDocenteOk() {
		Map json = [
			"nombre": "Claudia",
			"apellido": "Tessi",
			"dni": 12345678,
			"direccion": [
				"calle": "Las Virgenes",
				"numero": "1191",
				"piso": 0,
				"depto": "0"
			],
			"telefono": 2604432132,
			"nacimiento": "12-05-1966"
		]

		post("http://localhost:8080/tesis/docentes/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)

	}

	void testUpdateDocenteOk() {
		Map json = [
			"nombre": "Gaston",
			"apellido": "Urcola",
			"dni": 98765432,
			"direccion": [
				"calle": "Colon",
				"numero": "356",
				"piso": 2,
				"depto": "C"
			],
			"telefono": 26144332234,
			"nacimiento": "29-04-2005"
		]

		post("http://localhost:8080/tesis/docentes/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201
		
		ValidatePostAndPut(json)

		json.telefono = 2604567898

		put("http://localhost:8080/tesis/docentes/datos_personales/98765432", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)
		
		assertStatus 200

		ValidatePostAndPut(json)

	}

	void testGetDatosPersonales(){
		Map json = [
			"nombre": "Facundo",
			"apellido": "Castro",
			"dni": 44566778,
			"direccion": [
				"calle": "Mitre",
				"numero": "2356",
				"piso": 0,
				"depto": "0"
			],
			"telefono": 2604320897,
			"nacimiento": "03-10-2002"
		]

		post("http://localhost:8080/tesis/docentes/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201
		
		ValidatePostAndPut(json)

		get("http://localhost:8080/tesis/docentes/datos_personales/44566778", { 
			headers['Accept'] = 'application/json' 
			}
		)

		assertStatus 200

		JSONObject d = new JSONObject(response.getContentAsString()).response

		assert d
		assert d.nombre == json.nombre
		assert d.apellido == json.apellido
		assert d.dni == json.dni
		assert d.direccion.calle == json.direccion.calle
		assert d.direccion.numero == json.direccion.numero
		assert d.direccion.piso == json.direccion.piso
		assert d.direccion.depto == json.direccion.depto
		assert d.telefono == json.telefono
		assert d.nacimiento == json.nacimiento
	}
	
	void testAsignarEscuelaOk() {
		// Crear docente
		Map json = [
			"nombre": "Ana",
			"apellido": "Villegas",
			"dni": 15334324,
			"direccion": [
				"calle": "Gral. Paz",
				"numero": "61",
				"piso": 8,
				"depto": "6"
			],
			"telefono": 264445132,
			"nacimiento": "13-04-2005"
		]

		post("http://localhost:8080/tesis/docentes/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)
		
		// Crear escuela
		json = [
			"nombre": "Republica de Siria",
			"numero_ins": "1-567",
			"direccion": [
				"calle": "Republica de siria",
				"numero": "683",
			],
			"telefono": 2604667766,
			"seccion": 1
		]

		post("http://localhost:8080/tesis/escuelas/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		// Asignar escuela al docente
		json = [
			"dni": 15334324,
			"esc": "1-567",
			"attr1": "2",
			"attr2": "B",
			"nivel": "NP",
			"cargo": "docente"
		]

		post("http://localhost:8080/tesis/docentes/asignar_escuela", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidateEscActual(json)
		
		// Intento asignar escuela a un docente que ya tiene esa asignaci�n
		post("http://localhost:8080/tesis/docentes/asignar_escuela", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)
		
		assertStatus 304
		
		// Crear escuela
		json = [
			"nombre": "Fauso Burgos",
			"numero_ins": "1-554",
			"direccion": [
				"calle": "Los Sauces y Bentos",
				"numero": "s/n",
			],
			"telefono": 2604554455,
			"seccion": 5
		]

		post("http://localhost:8080/tesis/escuelas/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201
		
		// Asignar otra escuela al docente
		json = [
			"dni": 15334324,
			"esc": "1-554",
			"attr1": "5",
			"attr2": "A",
			"nivel": "NP",
			"cargo": "director"
		]

		post("http://localhost:8080/tesis/docentes/asignar_escuela", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201
		
		get("http://localhost:8080/tesis/docentes/escuelas/${json.dni}", {
			headers['Accept'] = 'application/json'
		}
		)

		assertStatus 200

		ValidateEscActual(json)
		
		ValidateGetEscuelas(json, 2)
	}
	
	void ValidatePostAndPut(json){
		get("http://localhost:8080/tesis/docentes/datos_personales/${json.dni}", { 
			headers['Accept'] = 'application/json' 
			}
		)

		assertStatus 200

		JSONObject d = new JSONObject(response.getContentAsString()).response

		assert d
		assert d.nombre == json.nombre
		assert d.apellido == json.apellido
		assert d.dni == json.dni
		assert d.direccion.calle == json.direccion.calle
		assert d.direccion.numero == json.direccion.numero
		assert d.direccion.piso == json.direccion.piso
		assert d.direccion.depto == json.direccion.depto
		assert d.telefono == json.telefono
		assert d.nacimiento == json.nacimiento
	}
	
	void ValidateGetEscuelas(json, cant){
		get("http://localhost:8080/tesis/docentes/escuelas/${json.dni}", {
			headers['Accept'] = 'application/json'
		}
		)

		assertStatus 200

		JSONArray escuelas = new JSONObject(response.getContentAsString()).response
		assert escuelas.size() == cant
	}
	
	void ValidateEscActual(json){
		List<DocenteE> escuelas = DocenteE.findAllByDniAndActual(json.dni,true)

		DocenteE d = escuelas.find { e -> e.esc == json.esc }
		
		assert d
		assert d.dni == json.dni
		assert d.esc == json.esc
		assert d.attr1 == json.attr1
		assert d.attr2 == json.attr2
		assert d.nivel == json.nivel
		assert d.cargo == json.cargo
	}

}