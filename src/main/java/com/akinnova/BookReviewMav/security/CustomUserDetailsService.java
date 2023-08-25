package com.akinnova.BookReviewMav.security;

import com.akinnova.BookReviewMav.entity.UserEntity;
import com.akinnova.BookReviewMav.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("user with this username not found" + username));

        //I do not know if subsequent code will work
        Set<GrantedAuthority> authorities = userEntity.getRoles().stream()
                .map((role)-> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toSet());

        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }

}
