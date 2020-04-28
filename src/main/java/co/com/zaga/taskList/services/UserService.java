package co.com.zaga.taskList.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.zaga.taskList.model.Task;
import co.com.zaga.taskList.model.User;
import co.com.zaga.taskList.model.dto.TaskDto;
import co.com.zaga.taskList.model.dto.UserDto;
import co.com.zaga.taskList.repository.UserRepository;

/***
 * Service for handling  {@link User} authentication
 * @author jose.nino
 *
 */
@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
		createUser();
	}

	/**
	 * Validate  {@link User} credentials
	 * @param user username
	 * @param password user password
	 * @return {@link UserDto} model
	 */
	public UserDto validateCredentials(String user, String password) {

		return userRepository.findByName(user).map(u -> {
			UserDto userDto = new UserDto(-1, "");

			if (u.getPassword().equals(password)) {
				userDto.setId(u.getId());
				userDto.setName(u.getName());
				userDto.setJwtToken(
						JWTService.createJWT(String.valueOf(u.getId()), u.getName()));
			}
			return userDto;
		}).orElse(new UserDto(-1, ""));
	}

	/**
	 * Retrieve the especific {@link User}
	 * @param id {@link User} id
	 * @return {@link Optional<User>}
	 */
	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);
	}

	/**
	 * Retrieve {@link User} {@link Task} 
	 * @param id {@link User} id
	 * @return {@link List<TaskDto>}
	 */
	public List<TaskDto> getTasks(Long id) {
		return userRepository.findById(id)
				.map(u -> u.getTasks().stream()
						.map(t -> new TaskDto(t.getId(), t.getTitle(), t.getDescription(), t.getEstimateDate(),
								t.getStatus(), new UserDto(t.getUser().getId(), t.getUser().getName())))
						.collect(Collectors.toList()))
				.orElse(null);
	}

	/**
	 * Create users in BD for testing purposes
	 */
	private void createUser() {
		User user = new User();
		user.setName("user1");
		user.setPassword("12345");
		userRepository.save(user);

		User user2 = new User();
		user2.setName("user2");
		user2.setPassword("54321");
		userRepository.save(user2);
	}
}
