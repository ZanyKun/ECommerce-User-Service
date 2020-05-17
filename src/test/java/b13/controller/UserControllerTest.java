package b13.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.netflix.client.http.HttpRequest;

import b13.dto.User;
import b13.service.UserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	
	@Autowired	MockMvc mvc;
	@MockBean	UserService service;
	@Mock		HttpRequest	request;
	@Mock		User user;
	@Mock		Page<User> userList;
	
	//Creating a user with no information passed
	@Test
	void createUserNoInformationPassed() throws Exception {
		when(service.createUser(user)).thenReturn(Optional.empty());
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotAcceptable());
	}
	
	//Creating a user with information passed in
	@Test
	void createUserInformationPassed() throws Exception {
		when(service.createUser(user)).thenReturn(Optional.of(new User()));
		mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}
	
	//Getting a user with a username that does not exist
	@Test
	void getUserByUsernameDoesNotExist() throws Exception {
		when(service.getUserByUsername(Mockito.anyString())).thenReturn(Optional.empty());
		mvc.perform(get("/users/test").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	//Getting a user with a username that exists
	@Test
	void getUserByUsernameExist() throws Exception {
		when(service.getUserByUsername(Mockito.anyString())).thenReturn(Optional.of(new User()));
		mvc.perform(get("/users/test").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	//Attempting to get a page of users but nothing is returned
	@Test
	void getUsersNoneFound() throws Exception{
		when(service.getUsers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.empty());
		mvc.perform(get("/users/search?offset=0&&limit=20").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	//Attempting to get a page of users and get one returned
	@Test
	void getUsersPageFound() throws Exception{
		when(service.getUsers(Mockito.anyInt(), Mockito.anyInt())).thenReturn(Optional.ofNullable(userList));
		mvc.perform(get("/users/?offset=0&&limit=20").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	//Deleting a user that does not exist
	@Test
	void deleteUserByUsernameDoesNotExist() throws Exception {
		when(service.deleteUserByUsername(Mockito.anyString())).thenReturn(false);
		mvc.perform(delete("/users/test").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}
	
	//Deleting a user that exists
	@Test
	void deleteUserByUsernameExist() throws Exception{
		when(service.deleteUserByUsername(Mockito.anyString())).thenReturn(true);
		mvc.perform(delete("/users/test").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	//Updating user information for user that exists
	@Test
	void updateUserByUsernameExist() throws Exception{
		when(service.updateUser(user)).thenReturn(Optional.of(new User()));
		mvc.perform(put("/users/test").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
}
