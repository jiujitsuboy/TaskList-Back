package co.com.zaga.taskList.model.dto;

import java.time.LocalDate;

import co.com.zaga.taskList.model.TaskStatus;

/**
 * Represent a {@link Task}
 * @author jose.nino
 *
 */
public class TaskDto {

	private long id;
	private String title;
	private String description;
	private LocalDate estimateDate;
	private TaskStatus status;
	private UserDto user;

	public TaskDto() {
	}

	public TaskDto(long id, String title, String description, LocalDate estimateDate, TaskStatus status, UserDto user) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.estimateDate = estimateDate;
		this.status = status;
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getEstimateDate() {
		return estimateDate;
	}

	public void setEstimateDate(LocalDate estimateDate) {
		this.estimateDate = estimateDate;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}
	
	
}
