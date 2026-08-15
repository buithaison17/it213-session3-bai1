package com.example.bai1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public UserDTO getInfor(@RequestParam String email) {
        return userService.getInfor(email);
    }
}
