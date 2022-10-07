package ru.tele2.chat.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tele2.chat.model.UserChat;
import ru.tele2.chat.repository.UserChatRepository;
import ru.tele2.chat.service.UserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserChatRepository userChatRepository;
    @Override
    public UserChat registeredUser(String name) {
        UserChat userChat = new UserChat(name);
        return userChatRepository.save(userChat);

    }

    @Override
    public UserChat getUserByUsername(String username) {
        System.out.println("try get user " + username);
        UserChat userChat = userChatRepository.findByUserChatName(username);
        System.out.println("get user " + userChat);
        return userChat;
    }

    @Override
    public List<UserChat> getAll() {
        return userChatRepository.findAll();
    }

    @Override
    public UserChat getUserById(Integer userChatID) {
        return userChatRepository.findById(userChatID).get();
    }
}
