package br.com.roberth.avaliacaoTecnica.exceptions;

public class PautaBadRequestException extends Exception {

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
