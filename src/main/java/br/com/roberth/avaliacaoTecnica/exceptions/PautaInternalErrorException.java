package br.com.roberth.avaliacaoTecnica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class PautaInternalErrorException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public PautaInternalErrorException() {
		super();
	}
	
	public PautaInternalErrorException(String msg) {
		super(msg);
	}
	
	public PautaInternalErrorException(Throwable t) {
		super(t);
	}
	
	public PautaInternalErrorException(Exception e) {
		super(e);
	}
	
	public PautaInternalErrorException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public PautaInternalErrorException(String msg, Exception e) {
		super(msg, e);
	}

}
