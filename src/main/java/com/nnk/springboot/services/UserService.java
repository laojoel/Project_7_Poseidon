package com.nnk.springboot.services;

import com.nnk.springboot.domains.User;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> loadAll() {
        return userRepository.findAll();
    }

    public Optional<User> load(int id) {
        return userRepository.findById(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void delete(int id){
        userRepository.deleteById(id);
    }

    public String getRemoteRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            GrantedAuthority authority = authentication.getAuthorities().iterator().next();
            return authority.getAuthority().toLowerCase().replace("role_", "");
        }
        return "";
    }
}
