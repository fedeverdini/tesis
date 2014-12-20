package personal

import java.util.Map;

import org.codehaus.groovy.grails.web.json.JSONObject

import personal.alumno.*;
import utils.exception.*;
import utils.validator.Validator;

class AlumnoController extends PersonaController{

	AlumnoService alumnoService

	def saveDatosPersonales = {

		Map resp

		try{
			def data = request.getJSON()
			Validator.validateAlumnoData(data)

			def alumno = alumnoService.saveAlumno(data)

			if(!alumno) {
				throw new Exception("No se pudo crear el alumno con DNI: $data.dni")
			}

			resp = [response:"Alumno creado", status:201]
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}

	def delete = {
		// TODO: Analizar necesidades para borrar datos de un alumno
	}

	def updateDatosPersonales = {

		Map resp

		try{
			def dni = params.dni
			def data = request.getJSON()

			if(Validator.checkType(dni, "Long") != Validator.checkType(data.dni, "Long")){
				throw new BadRequestException("El campo DNI no se puede actualizar")
			}

			Validator.validateAlumnoData(data)

			def alumno = alumnoService.updateAlumno(data)

			if(!alumno) {
				throw new Exception("Error al intentar actualizar informaci�n para el alumno con DNI: $data.dni")
			}

			resp = [response:"Datos actualizados", status:200]
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}

	def getDatosPersonales = {

		Map resp

		try {
			Long dni = Validator.checkType(params.dni, "Long")

			Alumno a = alumnoService.getDatosPersonales(dni)

			if(a) {
				resp = [response:a.ToMap(), status:200]
			} else {
				throw new NotFoundException("El alumno con DNI: $dni no existe")
			}
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}

	def getEscuelaActual = {

		Map resp

		try {
			Long dni = Validator.checkType(params.dni, "Long")

			AlumnoE actual = alumnoService.getEscuelaActual(dni)

			if(actual) {
				resp = [response:actual.ToMap(), status:200]
			} else {
				throw new NotFoundException("El alumno con DNI: $dni no tiene asignada una escuela actual")
			}
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}

	def getEscuelas = {

		Map resp

		try {
			Long dni = Validator.checkType(params.dni, "Long")

			List<AlumnoE> escuelas = alumnoService.getEscuelas(dni)

			if(escuelas.size()) {
				resp = [response:AlumnoE.ToMap(escuelas), status:200]
			} else {
				throw new NotFoundException("No se encontraron escuelas para el alumno con DNI: $dni")
			}
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}

	def asignarEscuela = {

		Map resp

		try {
			def data = request.getJSON()
			Validator.validateEscuelaOperationData(data)

			AlumnoE escuela = alumnoService.asignarEscuela(data)

			if(escuela) {
				resp = [response:escuela.ToMap(), status:201]
			} else {
				throw new NotFoundException("No se encontraron datos para el alumno con DNI: $dni")
			}
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}

	def cambiarEscuela = {

		Map resp

		try {
			def data = request.getJSON()
			Validator.validateEscuelaOperationData(data)

			AlumnoE escuela = alumnoService.cambiarEscuela(data)
			
			// No verifico si escuela = null porque lo hace dentro del m�todo
			resp = [response:escuela.ToMap(), status:200]
			
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}
	
	def inasistencias = {

		Map resp

		try {
			def data = request.getJSON()
			Validator.validateInasistencias(data)

			Inasistencia i = alumnoService.inasistencias(data)

			// No verifico si escuela = null porque lo hace dentro del m�todo
			resp = [response:"Inasistencias cargadas correctamente", status:201]

		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}

}
