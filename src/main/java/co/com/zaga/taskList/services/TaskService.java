package co.com.zaga.taskList.services;


import java.util.stream.Collectors;

import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import co.com.zaga.taskList.model.Task;
import co.com.zaga.taskList.model.TaskStatus;
import co.com.zaga.taskList.model.User;
import co.com.zaga.taskList.model.dto.PageTaskInfo;
import co.com.zaga.taskList.model.dto.TaskDto;
import co.com.zaga.taskList.model.dto.UserDto;
import co.com.zaga.taskList.repository.TaskRepository;

/***
 * Service for handling {@link Task} per User
 * @author jose.nino
 *
 */
@Service
public class TaskService {

	private UserService userService;
	private TaskRepository taskRepository;
	private Validator validator;

	@Autowired
	public TaskService(TaskRepository taskRepository, UserService userService) {
		this.taskRepository = taskRepository;
		this.userService = userService;
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	/**
	 * Create a new {@link Task} for a specific user
	 * @param taskDto {@link Task} Model
	 * @return {@link Long} - id of the new {@link Task} created
	 */
	public Long createTask(TaskDto taskDto) {
		
		Task task = new Task();
		task.setTitle(taskDto.getTitle());
		task.setDescription(taskDto.getDescription());
		task.setStatus(taskDto.getStatus());
		task.setEstimateDate(taskDto.getEstimateDate());
		task.setUser(userService.getUser(taskDto.getUser().getId()).orElse(new User()));

		String errors = validator.validate(task).stream().map(v -> v.getMessage()).collect(Collectors.joining(","));

		if (errors != null && errors.length() > 0) {
			throw new RuntimeException(errors);
		}
		return taskRepository.save(task).getId();
	}

	/**
	 * Update the status of an existing {@link Task}
	 * @param id {@link Task} id
	 * @param taskStatus new {@link TaskStatus} of the {@link Task} 
	 */
	public void updateTaskStatus(Long id, TaskStatus taskStatus) {
		Task task = taskRepository.getOne(id);
		task.setStatus(taskStatus);
		taskRepository.save(task);
	}

	/**
	 * Retrieve a {@link Task} 
	 * @param id {@link Task} id
	 * @return {@link TaskDto}
	 */
	public TaskDto getTask(Long id) {
		Task task = taskRepository.getOne(id);

		return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getEstimateDate(),
				task.getStatus(), new UserDto(task.getUser().getId(), task.getUser().getName()));
	}

	/**
	 * Retrieve all the {@link Task} for an specific {@link USer}
	 * @param id {@link User} id
	 * @param pageRequest {@link Pageable} object
	 * @return {@link PageTaskInfo}
	 */
	public PageTaskInfo getTasks(Long id, Pageable pageRequest) {

		Page<Task> pageTasks = taskRepository.findByUser(userService.getUser(id).orElse(new User()), pageRequest);

		return new PageTaskInfo(pageTasks.getNumber(), pageTasks.getTotalPages(),
				pageTasks.getContent().stream()
						.map(t -> new TaskDto(t.getId(), t.getTitle(), t.getDescription(), t.getEstimateDate(),
								t.getStatus(), new UserDto(t.getUser().getId(), t.getUser().getName())))
						.collect(Collectors.toList()));

	}

}
