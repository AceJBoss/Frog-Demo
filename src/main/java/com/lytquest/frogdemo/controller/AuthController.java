package com.lytquest.frogdemo.controller;

import com.lytquest.frogdemo.config.JwtToken;
import com.lytquest.frogdemo.dto.JwtRequest;
import com.lytquest.frogdemo.dto.JwtResponse;
import com.lytquest.frogdemo.entity.User;
import com.lytquest.frogdemo.exception.ResourceNotFoundException;
import com.lytquest.frogdemo.repository.UserRepository;
import com.lytquest.frogdemo.service.impl.MyUserDetailsService;
import com.lytquest.frogdemo.service.impl.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtToken jwtToken;
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = myUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        Optional<User> userData = userRepository.findByUsername(authenticationRequest.getUsername());

        final String token = jwtToken.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(userData.get().getId(), userData.get().getFullname(), userData.get().getEmail(), userData.get().getUsername(), userData.get().getPassword(), token));

    }

    @PostMapping("/register")
    public User signUp(@RequestBody User user, @PathVariable Long roleId) throws ResourceNotFoundException {
        String password = user.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodedPassword);
        return userService.createAdminAccount(user, roleId);
    }


    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

