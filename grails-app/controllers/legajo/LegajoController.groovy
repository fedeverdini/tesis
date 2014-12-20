package legajo

import java.util.Map;

import org.codehaus.groovy.grails.web.json.JSONObject

import utils.exception.*;
import utils.validator.Validator;

class LegajoController {
	
	LegajoService legajoService

	def addLegajoInfo = {
		Map resp

		try{
			def data = request.getJSON()
			Validator.validateLegajoData(data)

			def legajo = legajoService.addLegajoInfo(data)

			if(!legajo) {
				throw new Exception("No se pudo crear legajo para el alumno con DNI: $data.dni")
			}

			resp = [response:"Legajo creado", status:201]
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}
	
	def getLegajoInfo = {
		Map resp

		try{
			def dni = Validator.checkType(params.dni, "Long")
			
			def legajo = legajoService.getLegajoInfo(dni)

			if(!legajo) {
				throw new Exception("No se pudo obtener legajo para el alumno con DNI: $data.dni")
			}

			resp = [response:legajo, status:200]
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}
	
	void apiResponse(Map resp) {
		render (status:resp.status, contentType:"application/json", encoding:"UTF-8", view:"response"){ resp }
	}
}
