package personal

import java.util.Map;

abstract class PersonaController {
	
	static private def ping = {
		return [response:["response":"pong"], status:200]
	}
	
	void apiResponse(Map resp) {
		render (status:resp.status, contentType:"application/json", encoding:"UTF-8", view:"response"){ resp }
	}

}
