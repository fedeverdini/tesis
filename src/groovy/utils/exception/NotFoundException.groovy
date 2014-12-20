package utils.exception

class NotFoundException extends APIException {
	def status = 404
	
	def NotFoundException(message, error = "bad_request", cause = []) {
		super(message, error, cause)
	}
}
