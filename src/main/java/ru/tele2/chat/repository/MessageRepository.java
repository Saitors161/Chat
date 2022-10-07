package ru.tele2.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tele2.chat.model.Message;
import ru.tele2.chat.model.UserChat;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {
  @Query(value = " SELECT * FROM chat_app.messages ORDER BY date_of_created LIMIT ?1", nativeQuery = true)
  List<Message> findLast(Integer quantity);
}
