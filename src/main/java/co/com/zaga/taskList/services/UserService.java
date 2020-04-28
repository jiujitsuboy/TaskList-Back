package co.com.zaga.taskList.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.zaga.taskList.model.User;
import co.com.zaga.taskList.model.dto.TaskDto;
import co.com.zaga.taskList.model.dto.UserDto;
import co.com.zaga.taskList.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
		createUser();
	}

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

	public Optional<User> getUser(Long id) {
		return userRepository.findById(id);
	}

	public List<TaskDto> getTasks(Long id) {
		return userRepository.findById(id)
				.map(u -> u.getTasks().stream()
						.map(t -> new TaskDto(t.getId(), t.getTitle(), t.getDescription(), t.getEstimateDate(),
								t.getStatus(), new UserDto(t.getUser().getId(), t.getUser().getName())))
						.collect(Collectors.toList()))
				.orElse(null);
	}

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
