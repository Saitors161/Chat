package ru.tele2.chat.socket;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.tele2.chat.dto.MessageDto;
import ru.tele2.chat.service.MessageService;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CustomTextWebSocketHandler extends TextWebSocketHandler {
  private final MessageService messageService;
  private final Gson gson = new Gson();

  private ConcurrentHashMap<String, Set<WebSocketSession>> sessionDataForUsers = new ConcurrentHashMap<>();

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    super.afterConnectionEstablished(session);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    disconnect(session);
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

    super.handleTextMessage(session, message);
    if (message.getPayload().startsWith("login#")) {
      registerNewConnection(session, getUsername(message));
    } else {
      TextMessage msg = new TextMessage(gson.toJson(messageService.getAll()));
      session.sendMessage(msg);
    }
  }

  private String getUsername(TextMessage message) {
    return message.getPayload().toString().substring(6);
  }

  private void registerNewConnection(WebSocketSession session, String username) {
    Set<WebSocketSession> sessions = sessionDataForUsers.get(username);
    if (sessions == null) {
      Set<WebSocketSession> sessionSet = new HashSet<>();
      sessionSet.add(session);
      sessionDataForUsers.put(username, sessionSet);
      refreshListActiveUsersForAll();
    } else {
      sessions.add(session);
    }
  }

  private void disconnect(WebSocketSession session) {
    ArrayList<String> usernameArray = new ArrayList();
    sessionDataForUsers.forEach((username, setSessions) -> {
      if (setSessions.contains(session)) {
        boolean removed = setSessions.remove(session);
        if (removed && setSessions.size()==0) {
          usernameArray.add(username);
        }

      }
    });

    if (!usernameArray.isEmpty()) {
      Set<WebSocketSession> sessions = sessionDataForUsers.get(usernameArray.get(0));
      if (sessions.isEmpty()) {
        sessionDataForUsers.remove(usernameArray.get(0));
        refreshListActiveUsersForAll();
      } else {
        sessions.remove(session);
      }
    }

  }

  public List<String> getAllActiveUsers() {
    List<String> listUsernames = new ArrayList();
    sessionDataForUsers.forEach((username, setSession) -> {
      listUsernames.add(username);
    });
    return listUsernames;
  }

  private void refreshListActiveUsersForAll() {
    List<String> listActiveUsers = new ArrayList<>();
    listActiveUsers.add("usersList#");
    listActiveUsers.addAll(getAllActiveUsers());
    sessionDataForUsers.forEach((username, setSessions) -> {
      setSessions.forEach(session -> {
        try {
          session.sendMessage(new TextMessage(gson.toJson(listActiveUsers)));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    });
  }

  public void refreshMessageListForAll(List<MessageDto> messagesDto) {
    List<MessageDto> listMessages= new ArrayList<>();
    listMessages.add(new MessageDto(null,null,null,"usersList#"));
    listMessages.addAll(messagesDto);
    sessionDataForUsers.forEach((username, setSessions) -> {
      setSessions.forEach(session -> {
        try {
          session.sendMessage(new TextMessage(gson.toJson(messagesDto)));
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      });
    });
  }

}
