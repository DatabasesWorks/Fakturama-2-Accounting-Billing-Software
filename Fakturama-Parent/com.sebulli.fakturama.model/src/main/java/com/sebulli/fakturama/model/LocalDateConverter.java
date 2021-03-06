package com.sebulli.fakturama.model;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * This class converts a given {@link LocalDate} to a {@link java.sql.Date} and vice versa. Used
 * for auto conversion of entity attributes.
 * 
 * @see {@link Blogentry http://www.adam-bien.com/roller/abien/entry/new_java_8_date_and}
 *
 */

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDate date) {
        Instant instant = Instant.from(date);
        return (Date) Date.from(instant);
        
    }

    @Override
    public LocalDate convertToEntityAttribute(Date value) {
        LocalDate retval = null;
        if(value != null) {
            retval = value.toLocalDate();
        }
        return retval;
    }
}
