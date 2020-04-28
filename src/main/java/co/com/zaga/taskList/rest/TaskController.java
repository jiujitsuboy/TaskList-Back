package co.com.zaga.taskList.rest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.com.zaga.taskList.model.TaskStatus;
import co.com.zaga.taskList.model.dto.PageTaskInfo;
import co.com.zaga.taskList.model.dto.TaskDto;
import co.com.zaga.taskList.services.TaskService;

@RestController
@RequestMapping("/task")
@CrossOrigin(origins = "http://localhost:8090")
public class TaskController {

	private TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@PostMapping("/create")
	@ResponseStatus(HttpStatus.CREATED)
	public Long createTask(HttpServletResponse response, @RequestBody TaskDto taskDto) {
		return taskService.createTask(taskDto);
	}

	@PutMapping("/update/{id}/{taskStatus}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateTaskStatus(HttpServletResponse response, @PathVariable Long id,
			@PathVariable TaskStatus taskStatus) {
		taskService.updateTaskStatus(id, taskStatus);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TaskDto getTask(HttpServletResponse response, @PathVariable Long id) {
		return taskService.getTask(id);
	}

	@GetMapping("/all/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PageTaskInfo getTasks(HttpServletResponse response, @PathVariable Long id, Pageable pageRequest) {
		return taskService.getTasks(id, pageRequest);
	}

}
