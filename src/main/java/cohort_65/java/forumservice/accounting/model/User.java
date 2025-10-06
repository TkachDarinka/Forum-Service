package cohort_65.java.forumservice.accounting.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Getter
@Document(collection = "users")
public class User {
    @Id
    String login;
    @Setter
    String password;
    @Setter
    String firstName;
    @Setter
    String lastName;
    Set<Roles> roles;

    public User() {
        roles = new HashSet<>();
        roles.add(Roles.USER);
    }

    public User(String login, String password, String firstName, String lastName) {
        this();
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean addRoles(String role) {
        return roles.add(Roles.valueOf(role.toUpperCase()));
    }

    public boolean removeRoles(String role) {
        return roles.remove(Roles.valueOf(role.toUpperCase()));
    }
}