package ru.tele2.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tele2.chat.dto.MessageDto;
import ru.tele2.chat.mapper.ListMessageMapper;
import ru.tele2.chat.mapper.MessageMapper;
import ru.tele2.chat.model.Message;
import ru.tele2.chat.model.UserChat;
import ru.tele2.chat.repository.MessageRepository;
import ru.tele2.chat.repository.UserChatRepository;
import ru.tele2.chat.service.MessageService;
import ru.tele2.chat.service.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final ListMessageMapper listMessageMapper;

    @Override
    public List<MessageDto> getAll() {
        return listMessageMapper.toDto(messageRepository.findAll()) ;
    }

    @Override
    public void addMessage(MessageDto messageDto) {
        Message message = messageMapper.toModel(messageDto);
        message.setDateOfCreated(LocalDateTime.now());
        messageRepository.save(message);
    }

  @Override
  public List<MessageDto> getLast(Integer quantity) {

    return listMessageMapper.toDto(messageRepository,);
  }


}
