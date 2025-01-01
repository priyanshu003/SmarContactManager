package com.scm.helper;



public class ResourceNotFound extends RuntimeException {

    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound() {
        super("Resource not found");
    }


    // existing code

}
