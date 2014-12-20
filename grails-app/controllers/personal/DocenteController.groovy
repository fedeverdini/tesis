package personal

import java.util.Map;

import org.codehaus.groovy.grails.web.json.JSONObject

import personal.docente.*;
import utils.RedisService;
import utils.exception.*;
import utils.validator.Validator;

class DocenteController extends PersonaController{

	DocenteService docenteService

	def saveDatosPersonales = {

		Map resp

		try{
			def data = request.getJSON()
			Validator.validateDocenteData(data)

			def docente = docenteService.saveDocente(data)

			if(!docente) {
				throw new Exception("No se pudo crear el docente con DNI: $data.dni")
			}

			resp = [response:"Docente creado", status:201]
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}

	def delete = {
		// TODO: Analizar necesidades para borrar datos de un docente
	}

	def updateDatosPersonales = {

		Map resp

		try{
			def dni = params.dni
			def data = request.getJSON()

			if(Validator.checkType(dni, "Long") != Validator.checkType(data.dni, "Long")){
				throw new BadRequestException("El campo DNI no se puede actualizar")
			}

			Validator.validateDocenteData(data)

			def docente = docenteService.updateDocente(data)

			if(!docente) {
				throw new Exception("Error al intentar actualizar informaci�n para el docente con DNI: $data.dni")
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

			Docente d = docenteService.getDatosPersonales(dni)

			if(d) {
				resp = [response:d.ToMap(), status:200]
			} else {
				throw new NotFoundException("El docente con DNI: $dni no existe")
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

			List<DocenteE> escuelas = docenteService.getEscuelas(dni)

			if(escuelas.size()) {
				resp = [response:DocenteE.ToMap(escuelas), status:200]
			} else {
				throw new NotFoundException("No se encontraron escuelas para el docente con DNI: $dni")
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

			DocenteE escuela = docenteService.asignarEscuela(data)

			if(escuela) {
				resp = [response:escuela.ToMap(), status:201]
			} else {
				throw new NotFoundException("No se encontraron datos para el docente con DNI: $dni")
			}
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}

}
