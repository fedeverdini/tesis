package utils.exception

class ConflictException extends APIException {
	def status = 409
	
	def ConflictException(message, error = "conflict", cause = []) {
		super(message, error, cause)
	}
}
