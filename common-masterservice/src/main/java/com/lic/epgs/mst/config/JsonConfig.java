package com.lic.epgs.mst.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class JsonConfig {
	
	private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.simpleDateFormat(dateTimeFormat);
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
            builder.serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }
	
	 @Bean
	    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
	        return new Jackson2ObjectMapperBuilder();
	    }
	 
	 @Bean
	    public JavaTimeModule javaTimeModule() {
	        return new JavaTimeModule();
	    }

	    @Bean
	    public ObjectMapper objectMapper() {
	        return jackson2ObjectMapperBuilder()
	            .build()
	            .registerModule(new JavaTimeModule())
	            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	    }

}
