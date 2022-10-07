package ru.tele2.chat.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;
@Data
@Entity
@Table(name = "users_chat")
@NoArgsConstructor
public class UserChat {
    @Column(name = "name")
    @Id
    private String name;
    @OneToMany(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "user_chat_name")
    private Set<Message> messages;

    public UserChat(String name) {
        this.name = name;
    }
}
