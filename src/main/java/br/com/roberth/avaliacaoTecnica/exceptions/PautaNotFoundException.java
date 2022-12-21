package br.com.roberth.avaliacaoTecnica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PautaNotFoundException extends PautaInternalErrorException {
	
	private static final long serialVersionUID = 1L;
	
	public PautaNotFoundException() {
		super();
	}
	
	public PautaNotFoundException(String msg) {
		super(msg);
	}
	
	public PautaNotFoundException(Throwable t) {
		super(t);
	}
	
	public PautaNotFoundException(Exception e) {
		super(e);
	}
	
	public PautaNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public PautaNotFoundException(String msg, Exception e) {
		super(msg, e);
	}

}
