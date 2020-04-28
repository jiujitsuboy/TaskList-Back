package co.com.zaga.taskList.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.com.zaga.taskList.model.Task;
import co.com.zaga.taskList.model.User;

/**
 * {@link Task} Repository
 * @author jose.nino
 *
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	Page<Task> findByUser(User user, Pageable pageRequest);
}
