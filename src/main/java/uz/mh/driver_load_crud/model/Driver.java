package uz.mh.driver_load_crud.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Table("drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver{
    @Id
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String password;
    public void setPassword(String password){
        this.password = new BCryptPasswordEncoder().encode(password);
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
