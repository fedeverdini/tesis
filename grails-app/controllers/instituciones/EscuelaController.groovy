package instituciones

import java.util.Map;
import org.codehaus.groovy.grails.web.json.JSONObject

import utils.RedisService;
import utils.exception.*
import utils.validator.Validator

class EscuelaController {
	
	EscuelaService escuelaService

	def saveEscuela = {

		Map resp

		try{
			def data = request.getJSON()
			Validator.validateEscuelaData(data)
			
			def escuela = escuelaService.saveEscuela(data)

			if(!escuela) {
				throw new Exception("No se pudo crear la escuela: ${data}")
			}

			resp = [response:"Escuela creada", status:201]
			
		} catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}
	
	def getEscuela = {

		Map resp

		try{
			def numeroIns = Validator.checkType(params.numero_ins, "String")

			def escuela = escuelaService.getEscuela(numeroIns)

			if(escuela) {
				resp = [response:escuela.ToJSON(), status:200]
			} else {
				throw new NotFoundException("La escuela número: $numeroIns no existe")
			}
		}
		catch (APIException e){
			resp = [response:"ERROR: ${e.getMessage()}", status:e.getStatus()]
		} catch (Exception e){
			resp = [response:"ERROR: ${e.getMessage()}", status:500]
		} finally {
			apiResponse(resp)
		}
	}
	
	def updateEscuela = {

		Map resp

		try{
			def numeroIns = Validator.checkType(params.numero_ins, "String")
			def data = request.getJSON()
			Validator.validateEscuelaData(data)

			def escuela = escuelaService.updateEscuela(data)

			if(!escuela) {
				throw new Exception("No se pudo actualizar info para la escuela: ${data}")
			}

			resp = [response:"Escuela modificada", status:200]
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
