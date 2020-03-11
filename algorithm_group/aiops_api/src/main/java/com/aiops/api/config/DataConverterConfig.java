package com.aiops.api.config;

import com.aiops.api.common.type.StatisticsStep;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shuaiyu Yao
 * @create 2020-03-11 14:07
 */
@JsonComponent
public class DataConverterConfig extends JsonDeserializer<Date> {

    private final static Map<StatisticsStep, SimpleDateFormat> formatMap;

    static {
        formatMap = new HashMap<>();
        SimpleDateFormat minuteDateFormat = new SimpleDateFormat("yyyy-MM-dd HHmm");
        SimpleDateFormat hourDateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
        SimpleDateFormat dayDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat monthDateFormat = new SimpleDateFormat("yyyy-MM");
        formatMap.put(StatisticsStep.MINUTE, minuteDateFormat);
        formatMap.put(StatisticsStep.HOUR, hourDateFormat);
        formatMap.put(StatisticsStep.DAY, dayDateFormat);
        formatMap.put(StatisticsStep.MONTH, monthDateFormat);
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getText().trim();
        if ("".equals(value)) {
            return null;
        }

        try {
            if (value.matches("^\\d{4}-\\d{2}$")) {
                return formatMap.get(StatisticsStep.MONTH).parse(value);
            } else if (value.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                return formatMap.get(StatisticsStep.DAY).parse(value);
            } else if (value.matches("^\\d{4}-\\d{2}-\\d{2} {1}\\d{2}$")) {
                return formatMap.get(StatisticsStep.HOUR).parse(value);
            } else if (value.matches("^\\d{4}-\\d{2}-\\d{2} {1}\\d{4}$")) {
                return formatMap.get(StatisticsStep.MINUTE).parse(value);
            } else {
                throw new IllegalArgumentException("Invalid date value '" + value + "'");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date value '" + value + "'");
        }
    }
}