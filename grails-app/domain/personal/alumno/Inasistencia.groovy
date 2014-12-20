package personal.alumno

import java.util.ArrayList;
import java.util.Map;

import org.codehaus.groovy.grails.web.json.JSONObject;

class Inasistencia {
	Long dni
	String attr1 // [sala || grado || curso]
	String nivel
	Long inasistencias
	
	static mapping = {
		//id generator: 'assigned', name:'dni', column:'DNI'
	}
	
	@Override
	public boolean equals(Object compareObj) {
		if (!(compareObj instanceof Inasistencia)) {
			throw new IllegalArgumentException("The compareObj is a different type!")
		}
		Inasistencia other = (Inasistencia)compareObj
		return (
			(this.dni == other.dni) &&
			(this.attr1 == other.attr1) &&
			(this.nivel == other.nivel) 
		)
	}
}
