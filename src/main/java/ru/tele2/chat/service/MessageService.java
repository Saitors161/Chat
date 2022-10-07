package ru.tele2.chat.service;

import org.springframework.stereotype.Service;
import ru.tele2.chat.dto.MessageDto;
import ru.tele2.chat.model.Message;

import java.util.List;

@Service
public interface MessageService {
    List<MessageDto> getAll();
    void addMessage(MessageDto messageDto);
    List<MessageDto> getLast(Integer quantity);
}
