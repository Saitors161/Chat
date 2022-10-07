package ru.tele2.chat.service;

import org.springframework.stereotype.Service;
import ru.tele2.chat.model.UserChat;

import java.util.List;

@Service
public interface UserService {
    UserChat registeredUser(String name);
    UserChat getUserByUsername(String username);
    List<UserChat> getAll();
    UserChat getUserById(Integer userChatID);

}
