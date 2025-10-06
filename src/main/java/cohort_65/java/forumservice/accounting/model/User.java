package cohort_65.java.forumservice.accounting.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "user")
@Getter
@EqualsAndHashCode(of = {"login"})
public class User {
    @Indexed(unique = true)  // проверка на уникальный индекс
    String login;
    @Setter
    String password;
    @Setter
    String firstName;
    @Setter
    String lastName;
    Set<Role> roles;

    public User() {
        roles = new HashSet<>();
        roles.add(Role.USER);
    }

    public User(String login, String password, String firstName, String lastName) {
        this();
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

    }

    public boolean addRole(String role) {
        return roles.add(Role.valueOf(role.toUpperCase()));
    }

    public boolean removeRole(String role) {
        return roles.remove(Role.valueOf(role.toUpperCase()));
    }

}
