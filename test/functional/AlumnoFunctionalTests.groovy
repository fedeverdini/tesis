/**
 * @author fverdini
 */

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject
import org.codehaus.groovy.grails.web.json.JSONArray
import personal.alumno.*
import utils.parser.Parser

class AlumnoFunctionalTests extends functionaltestplugin.FunctionalTestCase {

	static final URL_BASE = "http://localhost:8080/"

	void testSaveAlumnoOk() {
		Map json = [
			"nombre": "Milagros",
			"apellido": "Sosa",
			"dni": 12345678,
			"direccion": [
				"calle": "Dorrego",
				"numero": "1361",
				"piso": 0,
				"depto": "0"
			],
			"telefono": 2604432132,
			"nacimiento": "13-04-2005"
		]

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{
				(json as JSON).toString()
			}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)
	}

	void testUpdateAlumnoOk() {
		Map json = [
			"nombre": "Matias",
			"apellido": "Tessi",
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

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{
				(json as JSON).toString()
			}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)

		json.telefono = 2604567898

		put("http://localhost:8080/tesis/alumnos/datos_personales/98765432", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{
				(json as JSON).toString()
			}
		}
		)

		assertStatus 200

		ValidatePostAndPut(json)
	}

	void testGetDatosPersonales(){
		Map json = [
			"nombre": "Jesus",
			"apellido": "Verdini",
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

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{
				(json as JSON).toString()
			}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)

		get("http://localhost:8080/tesis/alumnos/datos_personales/44566778", {
			headers['Accept'] = 'application/json'
		}
		)

		assertStatus 200

		JSONObject a = new JSONObject(response.getContentAsString()).response

		assert a
		assert a.nombre == json.nombre
		assert a.apellido == json.apellido
		assert a.dni == json.dni
		assert a.direccion.calle == json.direccion.calle
		assert a.direccion.numero == json.direccion.numero
		assert a.direccion.piso == json.direccion.piso
		assert a.direccion.depto == json.direccion.depto
		assert a.telefono == json.telefono
		assert a.nacimiento == json.nacimiento
	}

	void testAsignarEscuelaOk() {
		// Crear alumno
		Map json = [
			"nombre": "Javier",
			"apellido": "Azcurra",
			"dni": 45334324,
			"direccion": [
				"calle": "Gral. Paz",
				"numero": "61",
				"piso": 8,
				"depto": "6"
			],
			"telefono": 264445132,
			"nacimiento": "13-04-2005"
		]

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)

		// Crear escuela
		json = [
			"nombre": "Federico A. Suter",
			"numero_ins": "1-086",
			"direccion": [
				"calle": "Juan Suter",
				"numero": "100",
			],
			"telefono": 2604432132,
			"seccion": 1
		]

		post("http://localhost:8080/tesis/escuelas/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		// Asignar escuela al alumno
		json = [
			"dni": 45334324,
			"esc": "1-086",
			"attr1": "1",
			"attr2": "A",
			"nivel": "NP",
		]

		post("http://localhost:8080/tesis/alumnos/asignar_escuela", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidateEscActual(json)

	}

	void testCambiarEscuelaOk() {
		// Crear alumno
		Map json = [
			"nombre": "Alejandro",
			"apellido": "Arnaudo",
			"dni": 45323232,
			"direccion": [
				"calle": "Gral. Paz",
				"numero": "61",
				"piso": 8,
				"depto": "6"
			],
			"telefono": 264445132,
			"nacimiento": "13-04-2005"
		]

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)

		// Crear escuelas
		json = [
			"nombre": "Juan Palma",
			"numero_ins": "1-083",
			"direccion": [
				"calle": "El Toledano",
				"numero": "1500",
			],
			"telefono": 2604454332,
			"seccion": 1
		]

		post("http://localhost:8080/tesis/escuelas/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		json = [
			"nombre": "Rodolfo Iselin",
			"numero_ins": "1-010",
			"direccion": [
				"calle": "Belgrano",
				"numero": "200",
			],
			"telefono": 2604432122,
			"seccion": 1
		]

		post("http://localhost:8080/tesis/escuelas/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		// Asignar escuela al alumno
		json = [
			"dni": 45323232,
			"esc": "1-083",
			"attr1": "1",
			"attr2": "A",
			"nivel": "NP"
		]

		post("http://localhost:8080/tesis/alumnos/asignar_escuela", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidateEscActual(json)
		
		// Cambiar escuela
		json = [
			"dni": 45323232,
			"esc": "1-010",
			"attr1": "1",
			"attr2": "A",
			"nivel": "NP",
		]
		
		put("http://localhost:8080/tesis/alumnos/cambiar_escuela", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)
		
		assertStatus 200
		
		ValidateEscActual(json)
		
		ValidateGetEscuelas(json,2)

	}
	
	void testInasistencias(){
		
		// Crear alumno
		Map json = [
			"nombre": "Hernan",
			"apellido": "Balmes",
			"dni": 32787656,
			"direccion": [
				"calle": "Segovia",
				"numero": "261",
				"piso": 0,
				"depto": "0"
			],
			"telefono": 2604534567,
			"nacimiento": "13-04-1986"
		]

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidatePostAndPut(json)

		// Crear escuela
		json = [
			"nombre": "Josefa Correa",
			"numero_ins": "1-266",
			"direccion": [
				"calle": "El Toledano",
				"numero": "1478",
			],
			"telefono": 2604438887,
			"seccion": 9
		]

		post("http://localhost:8080/tesis/escuelas/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201
		
		// Intento cargar insasistencias sin asignar al alumno
		json = [
			"dni": 32787656,
			"attr1": "1",
			"nivel": "NS",
			"inasistencias":13
		]

		post("http://localhost:8080/tesis/alumnos/inasistencias", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 400
		
		// Asignar escuela al alumno
		json = [
			"dni": 32787656,
			"esc": "1-266",
			"attr1": "1",
			"attr2": "5",
			"nivel": "NS",
		]

		post("http://localhost:8080/tesis/alumnos/asignar_escuela", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		ValidateEscActual(json)

		// Cargo inasistencias para el alumno
		json = [
			"dni": 32787656,
			"attr1": "1",
			"nivel": "NS",
			"inasistencias":13
		]

		post("http://localhost:8080/tesis/alumnos/inasistencias", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)
		
		assertStatus 201
		
		// Intento cargar datos duplicados
		post("http://localhost:8080/tesis/alumnos/inasistencias", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)
		
		assertStatus 304
	
	}

	void ValidateGetEscuelas(json, cant){
		get("http://localhost:8080/tesis/alumnos/escuelas/${json.dni}", {
			headers['Accept'] = 'application/json'
		}
		)

		assertStatus 200

		JSONArray escuelas = new JSONObject(response.getContentAsString()).response
		assert escuelas.size() == cant
	}
	
	void ValidatePostAndPut(json){
		get("http://localhost:8080/tesis/alumnos/datos_personales/${json.dni}", {
			headers['Accept'] = 'application/json'
		}
		)

		assertStatus 200

		JSONObject a = new JSONObject(response.getContentAsString()).response

		assert a
		assert a.nombre == json.nombre
		assert a.apellido == json.apellido
		assert a.dni == json.dni
		assert a.direccion.calle == json.direccion.calle
		assert a.direccion.numero == json.direccion.numero
		assert a.direccion.piso == json.direccion.piso
		assert a.direccion.depto == json.direccion.depto
		assert a.telefono == json.telefono
		assert a.nacimiento == json.nacimiento
	}

	void ValidateEscActual(json){
		AlumnoE e = AlumnoE.findByDniAndActual(json.dni,true)

		assert e
		assert e.dni == json.dni
		assert e.esc == json.esc
		assert e.attr1 == json.attr1
		assert e.attr2 == json.attr2
		assert e.nivel == json.nivel
	}
}