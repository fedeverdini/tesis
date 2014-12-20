/**
 * @author fverdini
 */

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject
import personal.alumno.Alumno
import utils.parser.Parser

class ValidatorsFunctionalTests extends functionaltestplugin.FunctionalTestCase {
	void testValidadoresFails(){

		Map validJson = [
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

		// NOMBRE
		Map badJson = validJson
		badJson.nombre = 1234

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)

		assertStatus 400

		// APELLIDO
		badJson = validJson
		badJson.apellido = 1234

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)
		
		assertStatus 400

		// DNI
		badJson = validJson
		badJson.dni = "abcd"

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)
		
		assertStatus 400
		
		// CALLE
		badJson = validJson
		badJson.direccion.calle = 123

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)

		assertStatus 400
		
		// NÚMERO
		badJson = validJson
		badJson.direccion.numero = "123a"

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)

		assertStatus 400
		
		// PISO
		badJson = validJson
		badJson.direccion.piso = "12a"

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)

		assertStatus 400
		
		// DEPTO
		badJson = validJson
		badJson.direccion.depto = 3

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)

		assertStatus 400
		
		// TELÉFONO
		badJson = validJson
		badJson.telefono = "123b"

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)

		assertStatus 400
		
		// FECHA DE NACIMIENTO
		badJson = validJson
		badJson.nacimiento = "12-12-12"

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)

		assertStatus 400
		
		badJson.nacimiento = "31-02-2012"

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)

		assertStatus 400
		
		badJson.nacimiento = "1234567890"

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(badJson as JSON).toString()}
		}
		)

	}
}