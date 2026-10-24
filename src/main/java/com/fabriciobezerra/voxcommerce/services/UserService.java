package com.fabriciobezerra.voxcommerce.services;

import com.fabriciobezerra.voxcommerce.dto.UserDto;
import com.fabriciobezerra.voxcommerce.entities.Role;
import com.fabriciobezerra.voxcommerce.entities.User;
import com.fabriciobezerra.voxcommerce.projections.UserDetailsProjection;
import com.fabriciobezerra.voxcommerce.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = repository.searchUserAndRoleByEmail(username);

        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }

        User user = new User();
        user.setEmail(username);
        user.setPassword(result.get(0).getPassword());

        for (UserDetailsProjection projection : result) {
            user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
        }

        return user;
    }

    protected User authenticated() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
            String username = jwtPrincipal.getClaim("username");

            return repository.findByEmail(username).get();

        } catch (Exception e) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
    }

    @Transactional(readOnly = true)
    public UserDto getMe() {
        User user = authenticated();
        return new UserDto(user);
    }
}
