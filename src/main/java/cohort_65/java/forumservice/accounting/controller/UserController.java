package cohort_65.java.forumservice.accounting.controller;

import cohort_65.java.forumservice.accounting.dto.NewUserDto;
import cohort_65.java.forumservice.accounting.dto.UserDto;
import cohort_65.java.forumservice.accounting.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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

    @PutMapping("/user/{login}/role/{role}")
    public UserDto addRoleForUser(@PathVariable String login,
                                  @PathVariable String role) {
        return userService.changeRoleForUser(login, role,true);
    }

    @DeleteMapping("/user/{login}/role/{role}")
    public UserDto removeRoleForUser(@PathVariable String login,
                                     @PathVariable String role) {
        return userService.changeRoleForUser(login, role,false);
    }

    @PostMapping("/login")
    public UserDto login(Principal principal) {
        return userService.getUserByLogin(principal.getName());
    }
}