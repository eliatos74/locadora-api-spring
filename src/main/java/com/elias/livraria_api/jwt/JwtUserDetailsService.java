package com.elias.livraria_api.jwt;

import com.elias.livraria_api.entity.User;
import com.elias.livraria_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUsername(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String username) {
        User.Role role = userService.findRoleUsername(username);
        return JwtUtils.createToken(username,role.name().substring("ROLE_".length()));
    }
}
