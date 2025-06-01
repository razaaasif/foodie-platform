package com.foodie.commons.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for JSON serialization/deserialization with DateTime support
 * All exceptions are handled internally - no exceptions thrown to callers
 *
 * Created on 29/05/25.
 * @author : aasif.raza
 */
public final class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = createObjectMapper();

    private JsonUtils() {
        // Prevent instantiation
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    /**
     * Serialize object to JSON string
     * @param object the object to serialize
     * @return JSON string or empty string if serialization fails
     */
    public static String toJson(Object object) {
        if (object == null) {
            return "";
        }

        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.warn("Serialization failed for object type: {}", object.getClass().getSimpleName(), e);
            return "";
        }
    }

    /**
     * Deserialize JSON string to object
     * @param json JSON string
     * @param clazz target class
     * @return deserialized object or null if deserialization fails
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }

        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            logger.warn("Deserialization failed for type: {}", clazz.getSimpleName(), e);
            return null;
        }
    }

    /**
     * Format LocalDateTime to ISO string
     * @param dateTime the LocalDateTime to format
     * @return formatted string or empty string if formatting fails
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        try {
            return dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            logger.warn("DateTime formatting failed", e);
            return "";
        }
    }

    /**
     * Parse ISO string to LocalDateTime
     * @param dateTimeString the string to parse
     * @return parsed LocalDateTime or null if parsing fails
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return null;
        }

        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            logger.warn("DateTime parsing failed for string: {}", dateTimeString, e);
            return null;
        }
    }

    /**
     * Pretty print JSON string
     * @param json input JSON string
     * @return formatted JSON string or original string if processing fails
     */
    public static String prettyPrint(String json) {
        if (json == null) {
            return "";
        }

        try {
            Object jsonObject = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (JsonProcessingException e) {
            logger.warn("Pretty print failed", e);
            return json;
        }
    }
}