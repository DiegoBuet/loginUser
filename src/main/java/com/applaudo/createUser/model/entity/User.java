package com.applaudo.createUser.model.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;



@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "list_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstName", nullable = false,  length = 50)
    private String firstName;
    @Column(name = "lastName", nullable = false,  length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true,  length = 50)
    private String email;
    @Column(name = "phoneNumber", nullable = false,  length = 50)
    private String phoneNumber;
    @Column(name = "password", nullable = false,  length = 50)
    private String password;


}

