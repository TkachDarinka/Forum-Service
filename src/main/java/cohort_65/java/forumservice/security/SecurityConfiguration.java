
package cohort_65.java.forumservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity //включает возможности Spring Security в приложении
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //Метод создает бин SecurityFilterChain, который настраивает цепочку фильтров безопасности
        http.httpBasic(Customizer.withDefaults()); //Включение базовой HTTP-аутентификации по умолчанию (логин/пароль через браузерную форму)
        http.csrf(csrf -> csrf.disable()); //удаляет фильтр защиты CSRF из цепочки фильтров безопасности
        http.authorizeHttpRequests(authorize -> authorize
                //account endpoints
                .requestMatchers("/account/register").permitAll() //Доступ к /account/register разрешен всем без аутентификации
                .requestMatchers(HttpMethod.DELETE, "/account/user/{login}") //запросы к /account/user/{login} разрешены либо ADMIN, либо если имя текущего пользователя совпадает с параметром login из пути
                .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') or authentication.name == #login"))
                .requestMatchers(HttpMethod.PUT, "/account/user/{login}")
                .access(new WebExpressionAuthorizationManager("authentication.name == #login"))
                .requestMatchers(HttpMethod.PUT, "/account/user/{login}/role/{role}")
                .access(new WebExpressionAuthorizationManager("hasRole('ADMIN')"))
                .requestMatchers(HttpMethod.DELETE, "/account/user/{login}/role/{role}")
                .access(new WebExpressionAuthorizationManager("hasRole('ADMIN')"))
                .requestMatchers(HttpMethod.PUT, "/account/password")
                .access(new WebExpressionAuthorizationManager("authentication.name == #login"))

                //forum endpoints
                .requestMatchers(HttpMethod.POST, "/forum/post/{author}")
                .access(new WebExpressionAuthorizationManager("authentication.name == #author"))
                .requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")
                .access(new WebExpressionAuthorizationManager("hasRole('MODERATOR') or @postService.isAuthor(authentication.name, #id)"))
                .requestMatchers(HttpMethod.PUT,"/forum/post/{id}")
                .access(new WebExpressionAuthorizationManager("@postService.isAuthor(authentication.name, #id)"))
                .requestMatchers(HttpMethod.PUT,"/forum/post/{id}/comment/{author}")
                .access(new WebExpressionAuthorizationManager("authentication.name == #author"))
                .requestMatchers(HttpMethod.POST, "/forum/posts") //доступ без аутентификации
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/forum/posts")//доступ без аутентификации
                .permitAll()
                .anyRequest().authenticated()); //Для всех остальных запросов требуется аутентификация
        return http.build(); //Возвращается построенная цепочка фильтров безопасности
    }
}
