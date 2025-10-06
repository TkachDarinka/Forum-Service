package cohort_65.java.forumservice.security;

import cohort_65.java.forumservice.accounting.dao.UserRepository;
import cohort_65.java.forumservice.accounting.dto.exception.UserNotFoundException;
import cohort_65.java.forumservice.accounting.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);

        Collection<String> authorities = user.getRoles()
                .stream()
                .map(r -> "ROLE_" + r.name())
                .toList();

        return new User(username, user.getPassword(), AuthorityUtils.createAuthorityList(authorities));
    }
}