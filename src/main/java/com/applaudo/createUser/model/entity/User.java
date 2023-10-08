package com.applaudo.createUser.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import jakarta.persistence.Id;



@Data
@Entity
@Table(name = "list_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firsName", nullable = false,  length = 50)
    private String firsName;
    @Column(name = "lastName", nullable = false,  length = 50)
    private String lastName;

    @Column(name = "email", nullable = false,  length = 50)
    private String email;
    @Column(name = "phoneNumber",  length = 50)
    private String phoneNumber;
    @Column(name = "password", nullable = false,  length = 50)
    private String password;
}
