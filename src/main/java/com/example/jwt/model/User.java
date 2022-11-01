package com.example.jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "userName"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        }),
})
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Size(min = 3, max = 50)
        private String name;

        @NotBlank
        @Size(min = 3, max = 50)
        private String userName;

        @NotBlank
        @Size(max = 50)
        @Email
        private String email;

        @JsonIgnore // ẩn trường khỏi trình phân tích cú pháp Jackson
        @Size(min = 3, max = 5)
        @NotBlank
        private String passWord;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "role_user", joinColumns = @JoinColumn(name = "user_id"),
                                       inverseJoinColumns = @JoinColumn(name ="role_id"))
        Set<Role> roles = new HashSet<>();

        public User(String name, String username, String email, String encode) {
                this.name = name;
                this.userName=username;
                this.email = email;
                this.passWord=encode;
        }
}
