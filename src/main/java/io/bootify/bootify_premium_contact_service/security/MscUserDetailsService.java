package io.bootify.bootify_premium_contact_service.security;

import io.bootify.bootify_premium_contact_service.user.User;
import io.bootify.bootify_premium_contact_service.user.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class MscUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MscUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MscUserDetails loadUserByUsername(final String username) {
        final User user = userRepository.findByUsernameIgnoreCase(username);
        if (user == null) {
            log.warn("user not found: {}", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        final String role = "roleContact".equals(username) ? UserRoles.ROLE_CONTACT : UserRoles.ROLE_ADMIN;
        final List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
        return new MscUserDetails(user.getId(), username, user.getPassword(), authorities);
    }

}
