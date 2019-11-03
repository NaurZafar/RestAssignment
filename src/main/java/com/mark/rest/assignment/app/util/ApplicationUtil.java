package com.mark.rest.assignment.app.util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApplicationUtil {

    public static Date convertLocalDateToDate(LocalDate localDate) {
        ZoneId defaultZoneId = ZoneId.systemDefault();

        return null;

    }

    public static String convertObjectToJsonString(Object object)
            throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsString(object);
    }

}
