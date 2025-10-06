package cohort_65.java.forumservice.accounting.dto;

import lombok.*;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder //для удобного и читаемого создания объектов
public class UserDto {
    String login;
    String password;
    String firstName;
    String lastName;
    @Singular //позволяет добавлять по одному элементу в билдере (например, .tag("admin").tag("moder").tag("user")), а не всю коллекцию целиком
    Set<String> roles;
}
