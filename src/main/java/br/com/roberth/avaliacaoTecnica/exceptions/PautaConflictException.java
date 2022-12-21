package br.com.roberth.avaliacaoTecnica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PautaConflictException extends PautaInternalErrorException {
	
	private static final long serialVersionUID = 1L;
	
	public PautaConflictException() {
		super();
	}
	
	public PautaConflictException(String msg) {
		super(msg);
	}
	
	public PautaConflictException(Throwable t) {
		super(t);
	}
	
	public PautaConflictException(Exception e) {
		super(e);
	}
	
	public PautaConflictException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public PautaConflictException(String msg, Exception e) {
		super(msg, e);
	}


}
