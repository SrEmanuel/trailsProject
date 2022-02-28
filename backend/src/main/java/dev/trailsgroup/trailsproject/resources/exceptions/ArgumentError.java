package dev.trailsgroup.trailsproject.resources.exceptions;

import java.io.Serializable;


public class ArgumentError implements Serializable {
    private Integer status;
    private String field;
    private String message;

    public ArgumentError() {
    }

    public ArgumentError(Integer status, String field, String message) {
        this.status = status;
        this.field = field;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
