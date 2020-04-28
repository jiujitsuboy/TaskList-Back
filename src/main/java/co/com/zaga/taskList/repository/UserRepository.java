package co.com.zaga.taskList.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.zaga.taskList.model.User;

/**
 * {@link User} Repository
 * @author jose.nino
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{
  Optional<User> findByName(String name);

}
