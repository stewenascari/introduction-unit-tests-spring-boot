package br.com.introductionunittests.introductionunittestsspringboot.exception;

public class DataIntegratyViolationExceptionException extends RuntimeException{

    public DataIntegratyViolationExceptionException(String message){
        super(message);
    }
}
