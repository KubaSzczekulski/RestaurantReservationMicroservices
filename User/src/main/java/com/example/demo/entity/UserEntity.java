package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_entity")
public class UserEntity{
    @Id
    @Column(name = "user_id")
    private String userID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "email")
    private String email;


    public UserEntity(String userName, String userLastName, String telephoneNumber, String email) {
        this.firstName = userName;
        this.lastName = userLastName;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
    }


}
