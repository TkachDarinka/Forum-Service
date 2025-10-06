package cohort_65.java.forumservice.accounting.service;

import cohort_65.java.forumservice.accounting.dao.UserAccountRepository;
import cohort_65.java.forumservice.accounting.dto.UserRegisterDto;
import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.dto.exception.UserExistsException;
import cohort_65.java.forumservice.accounting.dto.exception.UserNotFoundException;
import cohort_65.java.forumservice.accounting.model.User;
import cohort_65.java.forumservice.accounting.dto.UserUpdateDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    final UserAccountRepository userAccountRepository;
    final ModelMapper modelMapper;

    @Override
    public UserDto registerUser(UserRegisterDto userRegisterDto) {
        if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
            throw new UserExistsException();
        }
        User user = new User(userRegisterDto.getLogin(), userRegisterDto.getPassword(), userRegisterDto.getFirstName(), userRegisterDto.getLastName());
        user = userAccountRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto removeUserByLogin(String login) {
        User user = userAccountRepository.getUserByLoginIgnoreCase(login).orElseThrow(UserNotFoundException::new);
        userAccountRepository.delete(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto getUserByLogin(String login) {
        User user = userAccountRepository.getUserByLoginIgnoreCase(login).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto updateUserByLogin(String login, UserUpdateDto userUpdateDto) {
        User user = userAccountRepository.findById(login)
                .orElseThrow(UserNotFoundException::new);
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if (userUpdateDto.getFirstName() != null) {
            user.setFirstName(userUpdateDto.getFirstName());
        }
        user = userAccountRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto changeRoleForUser(String login, String role, boolean isAddRole) {
        User user = userAccountRepository.findById(login)
                .orElseThrow(UserNotFoundException::new);
        boolean res = isAddRole ? user.addRole(role) : user.removeRole(role);
        if (res) user = userAccountRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }


}
