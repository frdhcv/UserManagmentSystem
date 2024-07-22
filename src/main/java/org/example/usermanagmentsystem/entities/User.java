package org.example.usermanagmentsystem.entities;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    private Long id;
    private String userName;
    private int age;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public User(String string, int age, Date createdAt, Date updatedAt) {
        this.userName = userName = string;
        this.age = age;
        this.createdAt=LocalDate.ofEpochDay(createdAt.getTime());
        this.updatedAt=LocalDate.ofEpochDay(updatedAt.getTime());
    }
}
