package br.com.roberth.avaliacaoTecnica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PautaBadRequestException extends PautaInternalErrorException {

	private static final long serialVersionUID = 1L;
	
	public PautaBadRequestException() {
		super();
	}
	
	public PautaBadRequestException(String msg) {
		super(msg);
	}
	
	public PautaBadRequestException(Throwable t) {
		super(t);
	}
	
	public PautaBadRequestException(Exception e) {
		super(e);
	}
	
	public PautaBadRequestException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public PautaBadRequestException(String msg, Exception e) {
		super(msg, e);
	}

}
