package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ObjectMapperUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object target){
        String result = null;
        try {
            result = mapper.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    public static  <T> T toObject(String json,Class<T> cls){
        T t = null;
        try {
            t = mapper.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return t;
    }
}
