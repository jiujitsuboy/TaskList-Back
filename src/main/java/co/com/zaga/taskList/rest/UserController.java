package co.com.zaga.taskList.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.zaga.taskList.model.dto.CredentialDto;
import co.com.zaga.taskList.model.dto.TaskDto;
import co.com.zaga.taskList.model.dto.UserDto;
import co.com.zaga.taskList.services.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:8090")
public class UserController {
	
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	public UserDto loginUser(@RequestBody CredentialDto credentialDto) {
		return userService.validateCredentials(credentialDto.getUser(), credentialDto.getPass());
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public List<TaskDto> getTasks(@PathVariable Long id) {
		return userService.getTasks(id);
	}

}
