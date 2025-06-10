package com.foodie.commons.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Created on 07/06/25.
 *
 * @author : aasif.raza
 */
@Getter
@Setter
public class EntityAlreadyExistsException extends RuntimeException {
    private final String entityName;
    private final String fieldName;
    private final Object fieldValue;

    public EntityAlreadyExistsException(String entityName, String fieldName, Object fieldValue) {
        super(String.format("%s already exists with %s: '%s'", entityName, fieldName, fieldValue));
        this.entityName = entityName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
