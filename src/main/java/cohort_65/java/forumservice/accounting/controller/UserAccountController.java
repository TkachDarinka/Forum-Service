package cohort_65.java.forumservice.accounting.controller;

import cohort_65.java.forumservice.accounting.dto.UserRegisterDto;
import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.dto.UserUpdateDto;
import cohort_65.java.forumservice.accounting.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor

public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody UserRegisterDto userRegisterDto) { //@RequestBody — для конвертации тела запроса в объект
        return userAccountService.registerUser(userRegisterDto);
    }

    @DeleteMapping("user/{login}")
    public UserDto removeUser(@PathVariable String login) { //@PathVariable позволяет получать значения переменных из пути запроса и использовать их как параметры метода контроллера
        return userAccountService.removeUserByLogin(login);
    }

    @GetMapping("user/{login}")
    public UserDto getUser(@PathVariable String login) {
        return userAccountService.getUserByLogin(login);
    }

    @PutMapping("user/{login}")
    public UserDto updateUser(@PathVariable String login,
                              @RequestBody UserUpdateDto userUpdateDto) {
        return userAccountService.updateUserByLogin(login, userUpdateDto);
    }

    @PutMapping("/user/{login}/role/{role}")
    public UserDto addRoleForUser(@PathVariable String login,
                                  @PathVariable String role) {
        return userAccountService.changeRoleForUser(login, role,true);
    }

    @DeleteMapping("/user/{login}/role/{role}")
    public UserDto removeRoleForUser(@PathVariable String login,
                                     @PathVariable String role) {
        return userAccountService.changeRoleForUser(login, role,false);
    }
}