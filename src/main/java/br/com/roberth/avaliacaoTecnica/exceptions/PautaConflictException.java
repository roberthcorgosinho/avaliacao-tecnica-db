package br.com.roberth.avaliacaoTecnica.exceptions;

public class PautaConflictException extends Exception {
	
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
