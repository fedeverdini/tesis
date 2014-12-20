package personal.alumno

import grails.converters.JSON;
import org.codehaus.groovy.grails.web.json.JSONObject;
import utils.nivel.Nivel

/**
 * @author fverdini
 *
 * Contiene datos escolares actuales
 * para un alumno en particular
 */

class AlumnoE {

	Long dni
	String esc
	String attr1 // [sala || grado || curso]
	String attr2 // [secci—n || divisi—n]
	String nivel
	Boolean actual

	static mapping = {
		//id generator: 'assigned', name:'dni', column:'DNI'
	}

	JSONObject ToJSON() {
		return ToMap() as JSON
	}

	Map ToMap() {
		
		Map map = [
			"dni": dni,
			"escuela": esc,
		]

		map.put(Nivel.getById(nivel).getAttr1(), attr1);
		map.put(Nivel.getById(nivel).getAttr2(), attr2);
		map.put("nivel", nivel)

		return map
	}
	
	static ArrayList<Map> ToMap(ArrayList escuelas){
		
		ArrayList<Map> esc = []
		
		escuelas.each{ e ->
			esc.add(e.ToMap())
		}
		
		return esc
	}
	
	@Override
	public boolean equals(Object compareObj) {
		if (!(compareObj instanceof AlumnoE)) {
			throw new IllegalArgumentException("The compareObj is a different type!")
		}
		AlumnoE other = (AlumnoE)compareObj
		return (
			(this.dni == other.dni) &&
			(this.esc == other.esc) &&
			(this.attr1 == other.attr1) &&
			(this.attr2 == other.attr2) &&
			(this.nivel == other.nivel) &&
			(this.actual == other.actual)
		)
	}
}
