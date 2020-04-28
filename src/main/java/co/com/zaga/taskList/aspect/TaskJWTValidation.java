package co.com.zaga.taskList.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import co.com.zaga.taskList.model.User;

import javax.servlet.http.HttpServletResponse;

/**
 * Aspect which allow or deny the {@link User} request, regarding the result of the validation token
 * @author jose.nino
 *
 */
@Aspect
@Component
public class TaskJWTValidation {	
	
	/**
	 * Inspect the Http header'tokenValidation' to retreive the result of the JWT validation 
	 * @param joinPoint
	 */
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
