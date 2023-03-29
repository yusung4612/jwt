package com.example.temipj.domain.admin;

import com.example.temipj.domain.Timestamped;
import com.example.temipj.domain.news.Choice;
import com.example.temipj.domain.news.News;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.*;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String adminName;

    @Column(nullable = false, unique = true)
    private String emailId;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<News> news;

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Choice> choice;

//    public Admin(String emailId, String admin_name, String encodedPassword) {
//        this.emailId = emailId;
//        this.admin_name = admin_name;
//        this.password = encodedPassword;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Admin admin = (Admin) o;
        return id != null && Objects.equals(id, admin.id);
    }

//    @Override
//    public int hashCode() {
//        return getClass().hashCode();
//    }

    public void nameUpdate(String username) {
        this.adminName = adminName;
    }

    public void passwordUpdate(String password) {
        this.password = password;
    }

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }
}