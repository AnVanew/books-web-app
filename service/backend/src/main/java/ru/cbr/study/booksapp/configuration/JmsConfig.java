package ru.cbr.study.booksapp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.ErrorHandler;
import ru.cbr.study.booksapp.service.JmsService;

import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.Session;

@Configuration
public class JmsConfig {

    @Bean(name = JmsService.JSON_QUEUE_LISTENER_FACTORY)
    public DefaultJmsListenerContainerFactory jsonQueueListenerFactory(
        @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
        MessageConverter jacksonJmsMessageConverter, ErrorHandler printErrorHandler) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setSessionTransacted(true);
        factory.setMessageConverter(jacksonJmsMessageConverter);
        factory.setErrorHandler(printErrorHandler);
        factory.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper objectMapper) {
        objectMapper.registerModule(new JavaTimeModule());
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Bean
    public ErrorHandler printErrorHandler() {
        return (Throwable t) -> {
            //ignore log.error(t.getMessage());
        };
    }


}
