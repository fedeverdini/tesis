package utils.nivel

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum Nivel implements Serializable {
	
	nivel_inicial("NI", "sala", "seccion"), 
	nivel_primario("NP", "grado", "seccion"),
	nivel_secundario("NS", "curso", "division")
	
	String id;
	String attr1;
	String attr2;
	
	private Nivel(String aId, String a1, String a2){
		this.id = aId;
		this.attr1 = a1;
		this.attr2 = a2;
	}

	public String getId() {
		return id;
	}
	
	public String getAttr1() {
		return attr1;
	}
	
	public String getAttr2() {
		return attr2;
	}
	
	public static Nivel getById(String id){
		for (int i = 0; i < values().length; i++) {
			Nivel nivel = values()[i];
			if (nivel.id.equals(id)) {
				return nivel;
			}
		}
		return null;	
	}
	
	@Override
	public String toString() {
		return id;
	}
}