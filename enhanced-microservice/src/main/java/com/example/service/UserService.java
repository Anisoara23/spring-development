package com.example.service;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repo.RoleRepository;
import com.example.repo.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication signin(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    public Optional<User> signup(String username, String password, String firstName, String lastName) {
        String encoded = passwordEncoder.encode(password);
        if(!userRepository.findByUsername(username).isPresent()){
            Optional<Role> role = roleRepository.findByRoleName("ROLE_CSR");
            return Optional.of(userRepository.save(
                    new User(username,
                            encoded,
                            role.get(),
                            firstName,
                            lastName)
            ));
        }
        return Optional.empty();
    }
}
