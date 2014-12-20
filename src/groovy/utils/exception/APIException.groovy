package utils.exception

class APIException extends RuntimeException {
	
	def status = 500
	def error
	def cause = []

	def APIException(message, error, cause) {
		super(message.toString())
		this.error = error
		this.cause = cause
	}

	def APIException(message){
		super(message)
	}

	def APIException() {
		super("internal_error")
	}
}
