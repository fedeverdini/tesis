
import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject
import org.codehaus.groovy.grails.web.json.JSONArray
import personal.alumno.*
import legajo.*
import utils.parser.Parser
import org.apache.commons.lang.RandomStringUtils


class LegajoFunctionalTests extends functionaltestplugin.FunctionalTestCase {
	
	String charset = (('A'..'Z') + ('0'..'9')).join()
	
	void testAddLegajoInfoOk(){
		
		// Crear alumno
		Map json = [
			"nombre": "Miguel",
			"apellido": "Perez",
			"dni": 33444555,
			"direccion": [
				"calle": "Necochea",
				"numero": "143",
				"piso": 0,
				"depto": "0"
			],
			"telefono": 2604123456,
			"nacimiento": "07-05-2010"
		]

		post("http://localhost:8080/tesis/alumnos/alta", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201

		// Crear escuela
		json = [
			"nombre": "María Axuliadora",
			"numero_ins": "1-345",
			"direccion": [
				"calle": "Zapata",
				"numero": "4100",
			],
			"telefono": 2604998877,
			"seccion": 7
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
			"dni": 33444555,
			"esc": "1-345",
			"attr1": "4",
			"attr2": "3",
			"nivel": "NS",
		]

		post("http://localhost:8080/tesis/alumnos/asignar_escuela", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201
		
		// Agregar info al legajo
		postLegajoInfo("NI","4","07-02-2014")
		postLegajoInfo("NI","5","07-03-2015")
		
		[1,2,3,4,5,6,7].each { 
			postLegajoInfo("NP",it.toString(),"07-03-${2015+it}")
		}
		
		[1,2,3,4].each {
			postLegajoInfo("NP",it.toString(),"17-03-${2015+it}")
			postLegajoInfo("NS",it.toString(),"17-03-${2022+it}")
		}
	}

	void postLegajoInfo(nivel, attr1, fecha){
		def json = [
			"dni": 33444555,
			"numero_ins": "1-345",
			"attr1": attr1,
			"nivel": nivel,
			"fecha":fecha,
			"observaciones": RandomStringUtils.random(100, charset.toCharArray())
		]

		post("http://localhost:8080/tesis/legajo/${json.dni}", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
			body{(json as JSON).toString()}
		}
		)

		assertStatus 201
		
		get("http://localhost:8080/tesis/legajo/${json.dni}", {
			headers['Accept'] = 'application/json'
			headers['Content-Type'] = 'application/json'
		}
		)
		
		assertStatus 200
	}
}
