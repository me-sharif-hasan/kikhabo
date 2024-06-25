package com.iishanto.kikhabo.domain.entities.common;

import com.iishanto.kikhabo.domain.entities.text.GroceryPlanningPromptResponse;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class SchemaEntity {
    public String getSchema() {
        Field[] fields = getClass().getDeclaredFields();
        String descriptor = Arrays.stream(fields).map(field ->
                """
                    "%s":"%s"
                """.formatted(field.getName(), field.getType().getSimpleName())).collect(Collectors.joining(","));
        return """
                {
                    %s
                }
                """.formatted(descriptor);
    }
}
