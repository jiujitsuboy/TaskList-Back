package co.com.zaga.taskList.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import co.com.zaga.taskList.services.JWTService;
import io.jsonwebtoken.Claims;


@Component
public class TaskListFilter extends OncePerRequestFilter {
	
	private static Set<String> skipURIs = Set.of("/user/login");
	private static String AUTH_HEADER = "Authorization";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		boolean tokenValidation = false;
		String uri = request.getRequestURI();				

		if (!skipURIs.contains(uri)) {
			
			String header = request.getHeader(AUTH_HEADER);			

			if (header == null || !JWTService.validateJWT(header)) {									
				tokenValidation = false;
			} else {
				tokenValidation = true;
				
				Claims claims = JWTService.decodeJWT(header);
				String token = JWTService.createJWT(claims.getId(), claims.getSubject());				
				
				response.setHeader(AUTH_HEADER, token);
			}
		}
		
		response.setHeader("tokenValidation",String.valueOf(tokenValidation));
		response.setHeader("Access-Control-Expose-Headers", "Authorization");
		filterChain.doFilter(request, response);
		
	}

}
