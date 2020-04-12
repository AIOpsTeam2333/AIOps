package com.aiops.api.config.json.serializer;

import com.aiops.api.common.enums.ScopeType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-07 16:02
 */
@JsonComponent
public class ScopeSerializer extends JsonSerializer<ScopeType> {

    @Override
    public void serialize(ScopeType scopeType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(scopeType.getFrontEndName());
    }
}
