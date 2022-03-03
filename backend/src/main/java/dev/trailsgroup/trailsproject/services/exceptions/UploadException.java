package dev.trailsgroup.trailsproject.services.exceptions;

public class UploadException extends RuntimeException{
    public UploadException(Object id){
        super("Houve um problema durante o envio do arquivo. Id: " + id);
    }

    public UploadException(String msg){
        super("Houve um problema durante o envio do arquivo. " + msg);
    }

}
