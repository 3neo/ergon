package com.jewel.ergon.jobs.utilis;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;




public class IsoTimestampToLocalDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getText();
        return ZonedDateTime.parse(dateString).withZoneSameInstant(ZoneId.of("Africa/Tunis")).toLocalDate(); // Convert ISO 8601 timestamp to LocalDate
    }
}
