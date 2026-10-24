package com.fabriciobezerra.voxcommerce.controllers;

import com.fabriciobezerra.voxcommerce.dto.UserDto;
import com.fabriciobezerra.voxcommerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe() {
        UserDto dto = service.getMe();
        return ResponseEntity.ok(dto);
    }
}
