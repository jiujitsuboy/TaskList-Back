package co.com.zaga.taskList.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class TaskJWTValidation {	
	
	@Before(value="execution(* co.com.zaga.taskList.rest.TaskController.*(..))")
	public void Validate(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		
		if(args!=null && args[0] instanceof HttpServletResponse) {			
			String header = ((HttpServletResponse)args[0]).getHeader("tokenValidation");
			boolean isTokenValid = Boolean.parseBoolean(header);
					
			if(!isTokenValid) {				
				throw new RuntimeException("Invalid JWT Token2");
			}			
		}		
	}

}
