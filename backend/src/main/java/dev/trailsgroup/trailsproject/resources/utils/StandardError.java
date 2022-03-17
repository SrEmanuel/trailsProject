package dev.trailsgroup.trailsproject.resources.utils;


import java.io.Serializable;

public class StandardError implements Serializable {
    private Integer status;
    private String message;
    private String path;

    public StandardError() {
    }

    public StandardError(Integer status, String message, String path) {
        super();
        this.status = status;
        this.message = message;
        this.path = path;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
