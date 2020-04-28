package co.com.zaga.taskList.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Database entity for {@link UserDto}
 * @author jose.nino
 *
 */
@Entity
public class User {

	@Id
	@GeneratedValue
	private long id;
	
	@NotNull
	@Size(max = 20, message = "name size between 1 and 20")
	private String name;
	
	private String password;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<Task> tasks;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
}
