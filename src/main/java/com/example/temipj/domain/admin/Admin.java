package com.example.temipj.domain.admin;

import com.example.temipj.domain.Timestamped;
import com.example.temipj.domain.news.News;
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
    private String adminName; // 관리자 이름

    @Column(nullable = false, unique = true)
    private String emailId; // 이메일 아이디

    @Column(nullable = false)
    private String password; // 비밀번호

    @Column(nullable = false)
    private String passwordConfirm; // 비밀번호 확인

    @OneToMany(mappedBy = "admin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<News> news;

//    public Admin(String emailId, String adminName, String encodedPassword, String encodePasswordConfirm) {
//        this.emailId = emailId;
//        this.adminName = adminName;
//        this.password = encodedPassword;
//        this.passwordConfirm = encodePasswordConfirm;
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

    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }
}