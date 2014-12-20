package utils.exception

class NoModificationException extends APIException{
	def status = 304
	
	def NoModificationException(message, error = "bad_request", cause = []) {
		super(message, error, cause)
	}
}
