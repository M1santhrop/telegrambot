package com.github.m1santhrop.telegrambot.repository.entity;

import static java.util.Objects.*;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "group_sub")
public class GroupSub {
    
    @Id
    private Integer id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "last_article_id")
    private Integer lastArticleId;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "group_x_user",
        joinColumns = @JoinColumn(name = "group_sub_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<TelegramUser> users;
    
    public void addUser(TelegramUser user) {
        if (isNull(users)) {
            users = new ArrayList<>();
        }
        users.add(user);
    }
}
