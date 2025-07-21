package com.employee.management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.employee.management.model.UserInfo;
import com.employee.management.repository.UserInfoRepository;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserInfo user = repository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    
    return new UserInfoUserDetails(user);
}
}

