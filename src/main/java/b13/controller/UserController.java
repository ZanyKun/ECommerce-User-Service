package b13.controller;

import java.util.List;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import b13.dto.User;
import b13.service.UserService;
import io.micrometer.core.annotation.Timed;
import lombok.AllArgsConstructor;

@Timed
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
	
	UserService service;
	
	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<?> createUser(@RequestBody User user, HttpRequest request){
		return service.createUser(user).map(u -> ResponseEntity.status(HttpStatus.CREATED)
				.header("Location", request.getURI() + "/" + u.getUsername())
				.build())
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());
	}
	
	@GetMapping(value = "/{username}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<User> getUser(@PathVariable String username){
		return ResponseEntity.of(service.getUserByUsername(username));
	}
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<List<User>> getUsers(@RequestParam(name = "offset", defaultValue = "0") int offset,
											   @RequestParam(name="limit", defaultValue = "10") int limit){
		return service.getUsers(offset, limit).map(users -> ResponseEntity.status(HttpStatus.OK).body(users.getContent()))
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@DeleteMapping(value = "/{username}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<User> deleteUser(@PathVariable String username){
		if(service.deleteUserByUsername(username))
			return ResponseEntity.status(HttpStatus.OK).build();
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
//		return service.deleteUserByUsername(username).map(user -> ResponseEntity.status(HttpStatus.OK).body(user))
//				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PutMapping(value = "/{username}")
	public ResponseEntity<?> updateUser(HttpRequest request, @PathVariable String username, @RequestBody User user){
		user.setUsername(username);
		return service.updateUser(user).map(u -> ResponseEntity.status(HttpStatus.OK).header("Location", request.getURI() + "/" + user.getUsername()).build())
				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build());
	}
	
}
