package com.socialnet.domain.models;

import java.io.IOException;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateSerializer extends JsonSerializer<Date> {

    private static DateTimeFormatter formatter = 
        DateTimeFormat.forPattern("yyyyMMdd");

    @Override
    public void serialize(Date value, JsonGenerator gen, 
                          SerializerProvider arg2)
        throws IOException, JsonProcessingException {

        gen.writeString(formatter.print(new DateTime(value)));
    
    }
}
