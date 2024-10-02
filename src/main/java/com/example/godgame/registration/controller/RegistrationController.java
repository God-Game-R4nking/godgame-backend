package com.example.godgame.registration.controller;

import com.example.godgame.registration.service.CodefAuthService;
import com.example.godgame.registration.service.RegistrationAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/identity")
public class RegistrationController {

    @Autowired
    private CodefAuthService authService;

    @Autowired
    private RegistrationAuthService verificationService;

    @PostMapping("/verify")
    public Map<String, Object> verifyIdentity(
            @RequestParam String name,
            @RequestParam String phoneNo,
            @RequestParam String identity,
            @RequestParam int telecom
    ) {
        String accessToken = authService.getAccessToken();
        return verificationService.verifyIdentity(accessToken, identity, name, phoneNo, telecom);
    }

    @PostMapping("/add-verify")
    public Map<String, Object> completeAuth(
            @RequestParam String name,
            @RequestParam String phoneNo,
            @RequestParam String identity,
            @RequestParam int telecom
    ) {
        String accessToken = authService.getAccessToken();
        return verificationService.addVerify(accessToken, identity, name, phoneNo, telecom);
    }
}