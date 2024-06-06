package com.github.xqplus.sample.common.exception;

public class ManagedEntityNotFoundException extends RuntimeException {

    public ManagedEntityNotFoundException() {
        super();
    }

    public ManagedEntityNotFoundException(String type, String name) {
        super(type + " '" + name + "' dose not exist");
    }
}
