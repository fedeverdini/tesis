package legajo

class Legajo {
	
	Long dni
	String numeroIns
	String attr1 // [sala || grado || curso]
	String nivel
	Date fecha
	String observaciones

	@Override
	public boolean equals(Object compareObj) {
		if (!(compareObj instanceof Legajo)) {
			throw new IllegalArgumentException("The compareObj is a different type!")
		}
		Legajo other = (Legajo)compareObj
		return (
			(this.dni == other.dni) &&
			(this.attr1 == other.attr1) &&
			(this.numeroIns == other.numeroIns) &&
			(this.fecha == other.fecha) &&
			(this.observaciones == other.observaciones) &&
			(this.nivel == other.nivel)
		)
	}
	
    static constraints = {
    }
}
