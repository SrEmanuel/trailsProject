package dev.trailsgroup.trailsproject.services.exceptions;

public class ClientUploadException extends RuntimeException{
    public ClientUploadException(Object id){
        super("Houve um problema durante o envio do arquivo. Id: " + id);
    }

    public ClientUploadException(String msg){
        super("Houve um problema durante o envio do arquivo. " + msg);
    }

}
