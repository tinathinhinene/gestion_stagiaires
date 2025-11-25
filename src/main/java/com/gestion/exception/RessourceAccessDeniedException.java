package com.gestion.exception;
//exception personnalisée pour les acces interdits aux stagaires 
// la classe est une exception personnalisée qui se comporte comme un runtimeexception
public class RessourceAccessDeniedException extends RuntimeException {
	public RessourceAccessDeniedException(String message) {
		super(message);
	}
}
