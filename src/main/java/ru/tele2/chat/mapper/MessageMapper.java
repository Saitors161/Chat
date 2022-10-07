package ru.tele2.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.tele2.chat.dto.MessageDto;
import ru.tele2.chat.model.Message;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(source = "dateOfCreated", target = "dateOfCreated", qualifiedByName = "localDateToString")
    MessageDto toDto (Message message);
    Message toModel(MessageDto messageDto);

    @Named("localDateToString")
    static String localDateToString(LocalDateTime localDateTime) {
        return localDateTime.toString();
    }
    @Named("stringToLocalDate")
    static LocalDateTime stringToLocalDate(String localDateTimeString) {
        return LocalDateTime.parse(localDateTimeString);
    }

}
