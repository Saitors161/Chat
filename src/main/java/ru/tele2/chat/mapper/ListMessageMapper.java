package ru.tele2.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.tele2.chat.dto.MessageDto;
import ru.tele2.chat.model.Message;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = MessageMapper.class)
public interface ListMessageMapper {
    List<MessageDto> toDto (List<Message> messageList);
    List<Message> toModel(List<MessageDto> dtoList);

}
