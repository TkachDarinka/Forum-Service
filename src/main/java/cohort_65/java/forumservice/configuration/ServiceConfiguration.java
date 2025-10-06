package cohort_65.java.forumservice.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ServiceConfiguration {

    @Bean //@Bean говорит Spring, что метод создает бин, который будет управляться контейнером Spring
    ModelMapper getmodelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) //прямое сопоставление полей (без необходимости использовать геттеры и сеттеры)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) //указываем уровень доступа к полям, чтобы ModelMapper мог получать доступ к приватным полям при маппинге
                .setMatchingStrategy(MatchingStrategies.STRICT); //устанавливаем строгую стратегию сопоставления: поля должны точно совпадать по имени и типу, чтобы быть смапленными
        return modelMapper; //возвращаем полностью настроенный бин ModelMapper для использования по всему приложению через внедрение зависимостей (@Autowired или конструктор)
    }

    /*
    ModelMapper будет строго и корректно сопоставлять поля, включая приватные,
    что облегчает преобразование данных между слоями приложения (например, из Entity в DTO и обратно)
     */

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
