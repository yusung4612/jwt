package com.example.temipj.domain.employee;

import com.example.temipj.domain.Timestamped;
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
public class Division extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @Column(nullable = false)
    private String division; // 팀 이름

//    @JoinColumn(nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Admin admin; // join 해야하나?



//    @JoinColumn(nullable = false)
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Employee> employee;

}
