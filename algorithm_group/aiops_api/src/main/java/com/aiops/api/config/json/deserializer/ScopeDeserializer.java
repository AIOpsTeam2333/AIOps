package com.aiops.api.config.json.deserializer;

import com.aiops.api.common.enums.ScopeType;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-07 15:41
 */
@JsonComponent
public class ScopeDeserializer extends JsonDeserializer<ScopeType> {

    @Override
    public ScopeType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getText();
        if (value == null) return null;
        value = value.trim();
        if ("".equals(value)) {
            return null;
        }

        try {
            return ScopeType.valueOf(value);
        } catch (Exception e) {
            value = value.toLowerCase();
            if (value.contains("instance")) return ScopeType.INSTANCE;
            if (value.contains("endpoint")) return ScopeType.ENDPOINT;
            if (value.contains("database")) return ScopeType.DATABASE;
            if (value.contains("service")) return ScopeType.SERVICE;
            return null;
        }
    }
}
