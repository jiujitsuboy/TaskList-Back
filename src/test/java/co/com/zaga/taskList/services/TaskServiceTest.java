package co.com.zaga.taskList.services;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.zaga.taskList.model.Task;
import co.com.zaga.taskList.model.TaskStatus;
import co.com.zaga.taskList.model.User;
import co.com.zaga.taskList.model.dto.TaskDto;
import co.com.zaga.taskList.model.dto.UserDto;
import co.com.zaga.taskList.repository.TaskRepository;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

	@Mock
	private TaskRepository taskRepository;
	@Mock
	private UserService userService;
	
	
	@InjectMocks
	private TaskService taskService; 
	
	public TaskServiceTest() {
		taskService = new TaskService(taskRepository,userService);
	}

	
	@Test
	public void shouldCreateTask() {
		Task expectedTask = createDefaultTask();
		TaskDto taskDto = createDefaultTaskDto();
		User user = createDefaultUser();
		
		when(userService.getUser(anyLong())).thenReturn(Optional.of(user));
		when(taskRepository.save(any(Task.class))).thenReturn(expectedTask);
		
		Long taskId = taskService.createTask(taskDto);
		
		assertEquals(expectedTask.getId(), taskId);		
		verify(taskRepository,atMostOnce()).save(any(Task.class));

	}

	@Test
	public void shouldUpdateTaskStatus() {
		Task expectedTask = createDefaultTask();
	
		when(taskRepository.getOne(anyLong())).thenReturn(expectedTask);
		when(taskRepository.save(any(Task.class))).thenReturn(expectedTask);
		
		taskService.updateTaskStatus(1L, TaskStatus.DONE);
		
		verify(taskRepository,atMostOnce()).save(any(Task.class));
	}

	@Test
	public void shouldGetTask() {

		Task expectedTaskDto = createDefaultTask();
						
		when(taskRepository.getOne(anyLong())).thenReturn(expectedTaskDto);
		
		TaskDto taskDto = taskService.getTask(expectedTaskDto.getId());
		
		assertNotNull(taskDto);
		assertEquals(expectedTaskDto.getId(), taskDto.getId());
	}

	private Task createDefaultTask() {
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
	
	private User createDefaultUser() {
		User user = new User();
		user.setId(1);
		user.setName("User 1");
		user.setPassword("12345");
		return user;
	}
	
	private TaskDto createDefaultTaskDto() {
		
		User user =createDefaultUser(); 
		UserDto userDto = new UserDto(user.getId(),user.getName());		
		
		TaskDto taskDto = new TaskDto();
		taskDto.setTitle("Task 1");
		taskDto.setDescription("This is a default task");
		taskDto.setEstimateDate(LocalDate.now().plusDays(2));
		taskDto.setStatus(TaskStatus.PENDING);
		taskDto.setUser(userDto);
		
		return taskDto;

	}

}
