package com.aiops.api.config.json.deserializer;

import com.aiops.api.common.kpi.KpiHelper;
import com.aiops.api.common.kpi.KpiIndicator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @author Shuaiyu Yao
 * @create 2020-04-20 19:54
 **/
@JsonComponent
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KpiIndicatorDeserializer extends JsonDeserializer<KpiIndicator> {

    private final KpiHelper kpiHelper;

    @Override
    public KpiIndicator deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getText();
        return kpiHelper.splitKpi(value);
    }
}
