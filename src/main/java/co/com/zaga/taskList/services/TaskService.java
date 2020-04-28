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

	public void updateTaskStatus(Long id, TaskStatus taskStatus) {
		Task task = taskRepository.getOne(id);
		task.setStatus(taskStatus);
		taskRepository.save(task);
	}

	public TaskDto getTask(Long id) {
		Task task = taskRepository.getOne(id);

		return new TaskDto(task.getId(), task.getTitle(), task.getDescription(), task.getEstimateDate(),
				task.getStatus(), new UserDto(task.getUser().getId(), task.getUser().getName()));
	}

	public PageTaskInfo getTasks(Long id, Pageable pageRequest) {

		Page<Task> pageTasks = taskRepository.findByUser(userService.getUser(id).orElse(new User()), pageRequest);

		return new PageTaskInfo(pageTasks.getNumber(), pageTasks.getTotalPages(),
				pageTasks.getContent().stream()
						.map(t -> new TaskDto(t.getId(), t.getTitle(), t.getDescription(), t.getEstimateDate(),
								t.getStatus(), new UserDto(t.getUser().getId(), t.getUser().getName())))
						.collect(Collectors.toList()));

	}

}
