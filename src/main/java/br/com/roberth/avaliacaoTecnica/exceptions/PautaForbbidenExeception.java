package br.com.roberth.avaliacaoTecnica.exceptions;

public class PautaForbbidenExeception extends Exception {

	private static final long serialVersionUID = 1L;

	public PautaForbbidenExeception() {
		super();
	}

	public PautaForbbidenExeception(String msg) {
		super(msg);
	}

	public PautaForbbidenExeception(Throwable t) {
		super(t);
	}

	public PautaForbbidenExeception(Exception e) {
		super(e);
	}

	public PautaForbbidenExeception(String msg, Throwable t) {
		super(msg, t);
	}

	public PautaForbbidenExeception(String msg, Exception e) {
		super(msg, e);
	}

}
