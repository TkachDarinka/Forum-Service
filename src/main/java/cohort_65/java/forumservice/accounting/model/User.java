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
@NoArgsConstructor
public class User {
    @Indexed(unique = true)  // проверка на уникальный индекс
    String login;
    @Setter
    String password;
    @Setter
    String firstName;
    @Setter
    String lastName;
    @Setter
    Set<String> roles = new HashSet<String>();

    public User(String login, String password, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

    }

}
