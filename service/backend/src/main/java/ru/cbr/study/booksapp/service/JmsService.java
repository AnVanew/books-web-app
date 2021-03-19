package ru.cbr.study.booksapp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import ru.cbr.study.book.dto.BookDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class JmsService {

    public static final String JSON_QUEUE_LISTENER_FACTORY = "jsonQueueListenerFactory";

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public void sendToQueue(BookDto bookDto){
        jmsTemplate.convertAndSend("BOOKS", bookDto);
    }

    @JmsListener(destination = "BOOKS", containerFactory = JSON_QUEUE_LISTENER_FACTORY)
    public void receiveJmsMessage(Message<BookDto> bookDto) throws JsonProcessingException {
        log.info(objectMapper.writeValueAsString(bookDto.getPayload()));
    }
}
