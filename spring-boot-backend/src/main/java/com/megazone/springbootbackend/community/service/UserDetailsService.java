package com.megazone.springbootbackend.community.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}