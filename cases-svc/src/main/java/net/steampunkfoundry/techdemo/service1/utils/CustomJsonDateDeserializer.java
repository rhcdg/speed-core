package net.steampunkfoundry.techdemo.service1.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class CustomJsonDateDeserializer extends JsonDeserializer<LocalDate> {

    private final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
            .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            .appendOptional(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
            .toFormatter();

    @Override
    public LocalDate deserialize(JsonParser jsonParser,
            DeserializationContext deserializationContext)
            throws IOException {
        return LocalDate.parse(jsonParser.getText(), dateFormatter);
    }
}
