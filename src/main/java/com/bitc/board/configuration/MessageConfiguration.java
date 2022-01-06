package com.bitc.board.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfiguration {

	   @Bean
	   public MessageSource messageSource() {
	      ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	      messageSource.setBasename("message");
	      messageSource.setDefaultEncoding("UTF-8");
	      
	      return messageSource;
	   }
	}

