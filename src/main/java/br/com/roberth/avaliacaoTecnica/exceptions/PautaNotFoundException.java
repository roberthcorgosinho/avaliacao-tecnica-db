package br.com.roberth.avaliacaoTecnica.exceptions;

public class PautaNotFoundException extends Exception {
	
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
