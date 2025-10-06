package cohort_65.java.forumservice.accounting.service;

import cohort_65.java.forumservice.accounting.dto.UserRegisterDto;
import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.dto.UserUpdateDto;

public interface UserAccountService {

    UserDto registerUser(UserRegisterDto userRegisterDto);

    UserDto removeUserByLogin(String login);

    UserDto getUserByLogin(String login);

    UserDto updateUserByLogin(String login, UserUpdateDto userUpdateDto);

    UserDto changeRoleForUser(String login, String role, boolean isAddRole);

}
