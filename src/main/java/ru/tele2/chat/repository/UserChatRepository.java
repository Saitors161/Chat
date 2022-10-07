package ru.tele2.chat.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.tele2.chat.model.UserChat;

public interface UserChatRepository extends JpaRepository<UserChat,Integer> {
    @Query(value = "SELECT name FROM chat_app.users_chat as Us where Us.name = ?1", nativeQuery = true)
    UserChat findByUserChatName(String name);
}
