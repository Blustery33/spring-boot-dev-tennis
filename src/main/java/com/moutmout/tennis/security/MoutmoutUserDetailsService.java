package com.moutmout.tennis.security;

import com.moutmout.tennis.entity.RoleEntity;
import com.moutmout.tennis.entity.UserEntity;
import com.moutmout.tennis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MoutmoutUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException{

       return userRepository.findOneWithRolesByLoginIgnoreCase(login)
                .map(this::createSecurityUser)
                .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur " + login + " n'a pas été trouvé."));

    }

    private User createSecurityUser(UserEntity userEntity) {
        List<SimpleGrantedAuthority> grantedRoles = userEntity
                .getRoles()
                .stream()
                .map(RoleEntity::getName)
                .map(SimpleGrantedAuthority::new)
                .toList();

        return new User(userEntity.getLogin(), userEntity.getPassword(), grantedRoles);
    }
}
