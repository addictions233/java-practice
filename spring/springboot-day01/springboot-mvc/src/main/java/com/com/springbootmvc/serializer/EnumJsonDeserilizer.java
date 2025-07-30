package com.com.springbootmvc.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class EnumJsonDeserilizer extends JsonDeserializer<Enum<?>> {
    /**
     * @param jsonParser   Parsed used for reading JSON content
     * @param ctx Context that can be used to access information about
     *             this deserialization activity.
     * @return Deserialized value
     */
    @Override
    public Enum<?> deserialize(JsonParser jsonParser, DeserializationContext ctx) throws IOException, JsonProcessingException {
        String text = jsonParser.getText();
        return null;
    }
}
