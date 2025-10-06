package cohort_65.java.forumservice.accounting.service;

import cohort_65.java.forumservice.accounting.dao.UserRepository;
import cohort_65.java.forumservice.accounting.dto.NewUserDto;
import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.dto.exception.UserNotFoundException;
import cohort_65.java.forumservice.accounting.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;
    final PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(NewUserDto newUserDto) {
        if (userRepository.getUserByLoginIgnoreCase(newUserDto.getLogin()).isPresent()) {
            throw new IllegalArgumentException("A user with this login already exists.");
        }
        User userAccount = modelMapper.map(newUserDto, User.class);

        String password = passwordEncoder.encode(userAccount.getPassword());
        userAccount.setPassword(password);

        userAccount = userRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto removeUserByLogin(String login) {
        User user = userRepository.getUserByLoginIgnoreCase(login).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByLogin(String login) {
        User user = userRepository.getUserByLoginIgnoreCase(login).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserByLogin(NewUserDto newUserDto, String login) {
        User user = userRepository.getUserByLoginIgnoreCase(login).orElseThrow(UserNotFoundException::new);
        String password = newUserDto.getPassword();
        if (password != null) {
            user.setPassword(password);
        }
        String firstName = newUserDto.getFirstName();
        if (firstName != null) {
            user.setFirstName(firstName);
        }
        String lastName = newUserDto.getLastName();
        if (lastName != null) {
            user.setLastName(lastName);
        }
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto changeRoleForUser(String login, String role, boolean isAddRole) {
        User user = userRepository.findById(login)
                .orElseThrow(UserNotFoundException::new);
        boolean res = isAddRole ? user.addRoles(role) : user.removeRoles(role);
        if (res) user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }


}
