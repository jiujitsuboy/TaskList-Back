package co.com.zaga.taskList.services;

import java.time.LocalDate;

import co.com.zaga.taskList.model.Task;
import co.com.zaga.taskList.model.TaskStatus;
import co.com.zaga.taskList.model.User;
import co.com.zaga.taskList.model.dto.TaskDto;
import co.com.zaga.taskList.model.dto.UserDto;

public abstract class AbstractServiceTest {
	
	protected Task createDefaultTask() {
		User user = createDefaultUser();
				
		Task task = new Task();
		task.setId(1L);
		task.setTitle("Task 1");
		task.setDescription("This is a default task");
		task.setEstimateDate(LocalDate.now().plusDays(2));
		task.setStatus(TaskStatus.PENDING);
		task.setUser(user);
		return task;

	}
	
	protected User createDefaultUser() {
		User user = new User();
		user.setId(1);
		user.setName("User 1");
		user.setPassword("12345");
		return user;
	}
	
	protected TaskDto createDefaultTaskDto() {
		
		UserDto userDto = createDefaultUserDto();		
		
		TaskDto taskDto = new TaskDto();
		taskDto.setTitle("Task 1");
		taskDto.setDescription("This is a default task");
		taskDto.setEstimateDate(LocalDate.now().plusDays(2));
		taskDto.setStatus(TaskStatus.PENDING);
		taskDto.setUser(userDto);
		
		return taskDto;

	}
	
	protected UserDto createDefaultUserDto() {
		User user =createDefaultUser(); 
		return new UserDto(user.getId(),user.getName());		
	}
	
	protected UserDto createInvalidUserDto() {
		return new UserDto(-1,"");		
	}

}
