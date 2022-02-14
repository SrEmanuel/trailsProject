package dev.trailsgroup.trailsproject.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(Object id){
        super("Resource not found. Id: " + id);
    }

    public ResourceNotFoundException(String msg){
        super("Resource not found. Error: " + msg);
    }

}
