class UrlMappings {

	static mappings = {

//		"/$controller/$action?/$id?"{
//			constraints {
//				// apply constraints here
//			}
//		}

		"/"(view:"/index")
		"500"(view:'/error')
		
		/******************************* ALUMNO **********************************/
		
		"/alumnos/alta"(controller:"alumno", parseRequest: true){
				action = [POST: 'saveDatosPersonales']
		}
		
		"/alumnos/datos_personales/$dni"(controller:"alumno", parseRequest: true){
			action = [GET: 'getDatosPersonales', PUT:'updateDatosPersonales']
		}
		
		"/alumnos/escuelas/$dni"(controller:"alumno", parseRequest: true){
			action = [GET: 'getEscuelas']
		}
		
		"/alumnos/escuelas/actual/$dni"(controller:"alumno", parseRequest: true){
			action = [GET: 'getEscuelaActual']
		}
		
		"/alumnos/asignar_escuela"(controller:"alumno", parseRequest: true){
			action = [POST: 'asignarEscuela']
		}
		
		"/alumnos/cambiar_escuela"(controller:"alumno", parseRequest: true){
			action = [PUT: 'cambiarEscuela']
		}
		
		"/alumnos/inasistencias"(controller:"alumno", parseRequest: true){
			action = [POST: 'inasistencias']
		}
		
		/*************************************************************************/
		
		/******************************* DOCENTES **********************************/
		
		"/docentes/alta"(controller:"docente", parseRequest: true){
				action = [POST: 'saveDatosPersonales']
		}
		
		"/docentes/datos_personales/$dni"(controller:"docente", parseRequest: true){
			action = [GET: 'getDatosPersonales', PUT:'updateDatosPersonales']
		}
		
		"/docentes/escuelas/$dni"(controller:"docente", parseRequest: true){
			action = [GET: 'getEscuelas']
		}
		
		"/docentes/asignar_escuela"(controller:"docente", parseRequest: true){
			action = [POST: 'asignarEscuela']
		}
		
		/*************************************************************************/
		
		/******************************* ESCUELA **********************************/
	
		"/escuelas/alta"(controller:"escuela", parseRequest: true){
			action = [POST: 'saveEscuela']
		}
		
		"/escuelas/$numero_ins"(controller:"escuela", parseRequest: true){
			action = [PUT: 'updateEscuela', GET: 'getEscuela']
		}
		
		
		/*************************************************************************/
		
		/******************************* LEGAJO **********************************/
		
			"/legajo/$dni"(controller:"legajo", parseRequest: true){
				action = [POST: 'addLegajoInfo', GET: 'getLegajoInfo']
			}
			
		/*************************************************************************/
		
	}
}
