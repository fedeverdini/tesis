import personal.alumno.Alumno;
import utils.RedisService;
import utils.parser.Parser
import grails.util.GrailsUtil
import utils.RedisService

class BootStrap {
	
	RedisService redisService

    def init = { servletContext ->
		
		if (GrailsUtil.environment.equals("test")) {
			
			redisService.flush()
		
//			Alumno a = new Alumno()
//			
//			a.dni = 87654321
//			a.nombre = "Naruto"
//			a.apellido = "Uzumaki"
//			a.calle = "Konoha"
//			a.numero = 123
//			a.piso = 1
//			a.depto = "A"
//			a.telefono = 2664577265
//			a.nacimiento = Parser.parseStringToDate("10-10-1997")
//			
//			a.save(flush:true)
		}
		
    }
    def destroy = {
    }
}
