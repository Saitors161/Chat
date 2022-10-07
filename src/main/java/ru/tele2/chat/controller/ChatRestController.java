package ru.tele2.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.tele2.chat.dto.MessageDto;
import ru.tele2.chat.model.UserChat;
import ru.tele2.chat.service.MessageService;
import ru.tele2.chat.service.UserService;
import ru.tele2.chat.socket.CustomTextWebSocketHandler;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ChatRestController {
  private final MessageService messageService;
  private final UserService userService;
  private final CustomTextWebSocketHandler appSocketHandler;

  public ChatRestController(MessageService messageService, UserService userService, CustomTextWebSocketHandler textWebSocketHandler) {
    this.messageService = messageService;
    this.userService = userService;
    this.appSocketHandler = textWebSocketHandler;
  }

  @GetMapping("/messages")
  @ResponseStatus(HttpStatus.OK)
  public List<MessageDto> getAllMessages() {
    return messageService.getAll();
  }

  @PostMapping("/messages")
  @ResponseStatus(HttpStatus.OK)
  public void createMessage(@RequestBody MessageDto messageDto) {

    messageService.addMessage(messageDto);
    appSocketHandler.refreshMessageListForAll(messageService.getAll());

  }

  @PostMapping("/users/{username}")
  @ResponseStatus(HttpStatus.OK)
  public UserChat createUser(@PathVariable String username) {
    if (username == null || username.equals("")) {
      return null;
    }
    return userService.registeredUser(username);
  }

  @GetMapping("/users/{username}")
  @ResponseStatus(HttpStatus.OK)
  public UserChat getUser(@PathVariable String username) {
    if (username == null || username.equals("")) {
      return null;
    }
    UserChat userChat = userService.getUserByUsername(username);
    if (userChat == null) {
      return new UserChat();
    }
    return userChat;
  }

  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  public List<UserChat> getAllUsers() {
    return userService.getAll();
  }

  @GetMapping("/login/users")
  @ResponseStatus(HttpStatus.OK)
  public List<String> getAllActiveUsers() {
    return appSocketHandler.getAllActiveUsers();
  }
}
