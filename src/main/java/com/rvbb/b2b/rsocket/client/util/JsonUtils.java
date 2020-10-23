package com.rvbb.b2b.rsocket.client.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Map;

@Slf4j
public class JsonUtils {

    public static ObjectMapper MAPPER = new ObjectMapper();
    public static ObjectMapper PRETTY_MAPPER = new ObjectMapper();
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        PRETTY_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static byte[] json(Object object) {
        try {
            return MAPPER.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not marshal object as JSON: " + object.getClass().getName(), e);
        }
    }

    public static String asString(Object object) {
        try {
            return (object != null) ? MAPPER.writeValueAsString(object) : "NULL";
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not marshal object as JSON: " + object.getClass().getName(), e);
        }
    }

    public static String marshalJsonAsPrettyString(Object object) {
        try {
            return (object != null) ? PRETTY_MAPPER.writeValueAsString(object) : "NULL";
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not marshal object as JSON: " + object.getClass().getName(), e);
        }
    }

    public static <T> T unJson(byte[] json, Class<T> type) {
        int length = 0;
        try {
            length = json.length;
            return MAPPER.readValue(json, type);
        } catch (Throwable e) {
            log.error("Unable to parse json from a array of bytes(length={}) to a object={}. Cause by ={} ", length,
                    type, e);
            return null;
        }
    }

    public static <T> void updateIfNonNull(T oldObj, T newObj, Class<T> tClazz) {
        Field[] fields = tClazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Object newValue = field.get(newObj);
                if (newValue != null) {
                    field.set(oldObj, newValue);
                }
            } catch (IllegalAccessException e) {
                log.warn(e.getMessage());
            }
        }
    }

    public static <T> byte[] serializeJSON(T object) throws IOException {
        return objectMapper.writeValueAsBytes(object);
    }

    public static <T> T deserializeJSON(byte[] json, TypeReference<T> tClass) throws IOException {
        return objectMapper.readValue(json, tClass);
    }

    public static <T> T map2Object(@SuppressWarnings("rawtypes") Map map, TypeReference<T> o) throws IOException {
        return objectMapper.convertValue(map, o);
    }

}
