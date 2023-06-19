package br.com.trier.springmatutino.services.exceptions;

public class ViolacaoIntegridade extends RuntimeException{
	
	public ViolacaoIntegridade(String message) {
		super(message);
	}

}