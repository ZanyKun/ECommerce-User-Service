package b13.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import b13.dto.User;

public interface UserService {
	
	Optional<User> createUser(User user);
	
	Optional<User> getUserByUsername(String username);
	
	boolean deleteUserByUsername(String username);
	
	Optional<Page<User>> getUsers(int offset, int limit);
	
	Optional<User> updateUser(User user);
	
}
