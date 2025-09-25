package cohort_65.java.forumservice.accounting.controller;

import cohort_65.java.forumservice.accounting.dto.NewUserDto;
import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody NewUserDto newUserDto) { //@RequestBody — для конвертации тела запроса в объект
        return userService.registerUser(newUserDto);
    }

    @DeleteMapping("user/{login}")
    public UserDto removeUser(@PathVariable String login) {
        return userService.removeUserByLogin(login);
    }

    @GetMapping("user/{login}")
    public UserDto getUser(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }

    @PutMapping("user/{login}")
    public UserDto updateUser(@PathVariable String login, @RequestBody NewUserDto newUserDto) {
        return userService.updateUserByLogin(newUserDto, login);
    }

    @PutMapping("user/{login}/role/{role}")
    public UserDto addRole(@PathVariable String login, @PathVariable String role, @RequestBody UserDto userDto) {
        return userService.addRoleByLogin(login, role);
    }

    @DeleteMapping("user/{login}/role/{role}")
    public UserDto removeRole(@PathVariable String login, @PathVariable String role) { //@PathVariable принимает два параметра пути через два @PathVariable
        return userService.removeRoleByLogin(login, role); //вызывается сервис для добавления роли и возвращается DTO
    }
}