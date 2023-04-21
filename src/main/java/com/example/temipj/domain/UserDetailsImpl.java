package com.example.temipj.domain;

import com.example.temipj.domain.admin.Admin;
import com.example.temipj.shared.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor

// Spring security에서 인증(Authentication)에 사용되는 인터페이스, 사용자 정보를 담고있는 객체
// 1. UserDetails 인터페이스를 구현하여 사용자 정보를 제공한다.
// 2. 인증 시 사용자가 입력한 아이디와 비밀번호를 검증한다.
// 3. 사용자의 권한 정보를 제공한다.
// 4. 사용자의 계정이 만료되었거나 잠겨있는지 검증한다.
// 5. 사용자 계정 정보와 권한 정보를 DB에서 조회하여 제공한다.
public class UserDetailsImpl implements UserDetails {

    private Admin admin;

    // 해당 권한 정보 가져오기
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Authority.ROLE_ADMIN.toString());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(authority);
        return authorities;
    }

    // DB에서 사용자의 비밀번호를 조회하여 반환
    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    // 사용자를 인증하는데 사용되는 이름을 반환
    @Override
    public String getUsername() {
        return admin.getEmailId();
    }

    // 사용자 계정이 만료되었는지 여부를 나타냄
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    // 사용자가 잠겨있는지 여부를 나타냄
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    // 사용자의 자격 증명(암호)이 만료되었는지 여부를 나타냄
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    // 사용 가능 여부를 나타냄
    @Override
    public boolean isEnabled() {
        return false;
    }

    // 이렇게 만들어진 UserDetails는 Spring Security에서 유저인증에 사용된다.
}
