package personal.docente

import java.util.ArrayList;
import java.util.Map;
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject
import utils.nivel.Nivel

/**
 * @author fverdini
 *
 * Contiene datos escolares actuales
 * para un docente en particular
 */

class DocenteE {


	Long dni
	String esc
	String attr1 // [sala || grado || curso]
	String attr2 // [secci�n || divisi�n]
	String nivel
	Boolean actual
	String cargo

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

		map.put(Nivel.getById(nivel).getAttr1(), attr1)
		map.put(Nivel.getById(nivel).getAttr2(), attr2)
		map.put("nivel", nivel)
		map.put("cargo", cargo)

		return map
	}

	static ArrayList<Map> ToMap(List<DocenteE> escuelas){
		
		ArrayList<Map> esc = []
		
		escuelas.each{ e ->
			esc.add(e.ToMap())
		}
		
		return esc
	}

	@Override
	public boolean equals(Object compareObj) {
		if (!(compareObj instanceof DocenteE)) {
			throw new IllegalArgumentException("The compareObj is a different type!")
		}
		DocenteE other = (DocenteE)compareObj
		return (
		(this.dni == other.dni) &&
		(this.esc == other.esc) &&
		(this.attr1 == other.attr1) &&
		(this.attr2 == other.attr2) &&
		(this.nivel == other.nivel) &&
		(this.actual == other.actual) &&
		(this.cargo == other.cargo)
		)
	}
}
