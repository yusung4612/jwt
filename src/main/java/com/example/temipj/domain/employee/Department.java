package com.example.temipj.domain.employee;

import com.example.temipj.domain.Timestamped;
import com.example.temipj.domain.admin.Admin;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String department; // 부서 이름

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Division division;

//    @JoinColumn
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Admin admin; // join 해야하나?
//
//    public boolean validateAdmin(Admin admin) {
//        return !this.admin.equals(admin);
//    }
}
