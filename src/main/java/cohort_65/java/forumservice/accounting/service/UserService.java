package cohort_65.java.forumservice.accounting.service;

import cohort_65.java.forumservice.accounting.dto.NewUserDto;
import cohort_65.java.forumservice.accounting.dto.UserDto;

public interface UserService {

    UserDto registerUser(NewUserDto newUserDto);

    UserDto removeUserByLogin(String login);

    UserDto getUserByLogin(String login);

    UserDto updateUserByLogin(NewUserDto newUserDto, String login);

    UserDto addRoleByLogin(String login, String role);

    UserDto removeRoleByLogin(String login, String role);
}
