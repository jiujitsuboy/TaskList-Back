package co.com.zaga.taskList.model.dto;

import java.util.List;

/**
 * Represent the number of {@link Task} and pagination distribution  
 * @author jose.nino
 *
 */
public class PageTaskInfo {

	private long nroPage;
	private long totalPages;
	private List<TaskDto> taskPerPage;

	public PageTaskInfo(long nroPage, long totalPages, List<TaskDto> taskPerPage) {
		this.nroPage = nroPage;
		this.totalPages = totalPages;
		this.taskPerPage = taskPerPage;
	}

	public long getNroPage() {
		return nroPage;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public List<TaskDto> getTaskPerPage() {
		return taskPerPage;
	}
	
	

}
