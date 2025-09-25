package cohort_65.java.forumservice.accounting.service;

import cohort_65.java.forumservice.accounting.dao.UserRepository;
import cohort_65.java.forumservice.accounting.dto.NewUserDto;
import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.dto.exception.UserNotFoundException;
import cohort_65.java.forumservice.accounting.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;
    final ModelMapper modelMapper;

    @Override
    public UserDto registerUser(NewUserDto newUserDto) {
        if (userRepository.getUserByLoginIgnoreCase(newUserDto.getLogin()).isPresent()) {
            throw new IllegalArgumentException("A user with this login already exists.");
        }
        User user = new User(newUserDto.getLogin(), newUserDto.getPassword(), newUserDto.getFirstName(), newUserDto.getLastName());
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
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
    public UserDto addRoleByLogin(String login, String role) {
        User user = userRepository.getUserByLoginIgnoreCase(login).orElseThrow(UserNotFoundException::new);
        Set<String> roles = user.getRoles(); //user.getRoles() - ссылкa на коллекцию ролей пользователя
        roles.add(role); //add() добавляет элемент в set, если его там ещё нет
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto removeRoleByLogin(String login, String role) {
        User user = userRepository.getUserByLoginIgnoreCase(login).orElseThrow(UserNotFoundException::new);
        Set<String> roles = user.getRoles();
        if (roles != null) {
            roles.remove(role);
        }
        user = userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }


}
