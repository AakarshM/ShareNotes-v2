package com.sharenotes.spring.controllers.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

/**
 * Created by Aakarsh on 8/17/17.
 */

@Component
public class ObjectWrapper {
    private ObjectMapper wrapper = new ObjectMapper();
    public ObjectMapper getWrapper(){
        return wrapper;
    }
}
