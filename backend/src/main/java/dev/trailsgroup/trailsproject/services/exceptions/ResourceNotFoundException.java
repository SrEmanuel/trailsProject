package dev.trailsgroup.trailsproject.services.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(Object id){
        super("Recurso não encontrado. Id: " + id);
    }

    public ResourceNotFoundException(String msg){
        super("Recurso não encontrado. Erro: " + msg);
    }

}
