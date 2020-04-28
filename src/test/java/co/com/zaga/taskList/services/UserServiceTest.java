package co.com.zaga.taskList.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import co.com.zaga.taskList.model.User;
import co.com.zaga.taskList.model.dto.UserDto;
import co.com.zaga.taskList.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends AbstractServiceTest {

	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;

	@Test
	public void shouldAuthorizeUser() {

		String user = "user1";
		String pass = "12345";
		
		User userExpected = createDefaultUser();
		
		when(userRepository.findByName(anyString())).thenReturn(Optional.of(userExpected));
		
		UserDto userValidated = userService.validateCredentials(user, pass);
		
		assertNotNull(userValidated);
		assertEquals(userExpected.getId(), userValidated.getId());
		
		
	}

	@Test
	public void shouldDenyUser() {
		String user = "user1";
		String pass = "1234";
		
		UserDto userExpected = createInvalidUserDto();
		
		when(userRepository.findByName(anyString())).thenReturn(Optional.empty());
		
		UserDto userValidated = userService.validateCredentials(user, pass);
		
		assertNotNull(userValidated);
		assertEquals(userExpected.getId(), userValidated.getId());
	}
	
}
