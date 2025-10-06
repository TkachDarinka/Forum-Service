package cohort_65.java.forumservice.accounting.dao;

import cohort_65.java.forumservice.accounting.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> { //User — тип сущности, String — тип ключа (id)
    Optional<User> getUserByLoginIgnoreCase(String login);

   }
