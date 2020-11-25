package engine.repository;

import engine.Dto.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserListRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);

}
