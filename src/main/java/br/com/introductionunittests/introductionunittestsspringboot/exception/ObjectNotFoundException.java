package br.com.introductionunittests.introductionunittestsspringboot.exception;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String message){
        super(message);
    }
}
