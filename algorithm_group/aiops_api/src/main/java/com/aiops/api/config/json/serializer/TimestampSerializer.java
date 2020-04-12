package com.aiops.api.config.json.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-07 18:09
 */
@JsonComponent
public class TimestampSerializer extends JsonSerializer<Timestamp> {


    @Override
    public void serialize(Timestamp timestamp, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNumber(timestamp.getTime());
    }
}
