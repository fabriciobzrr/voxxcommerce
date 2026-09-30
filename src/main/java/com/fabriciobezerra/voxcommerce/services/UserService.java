package com.fabriciobezerra.voxcommerce.services;

import com.fabriciobezerra.voxcommerce.entities.User;
import com.fabriciobezerra.voxcommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("Email não encontrado!");
        }

        return user;
    }
}
