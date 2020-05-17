package b13.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import b13.dao.UserRepository;
import b13.dao.util.Pagination;
import b13.dto.User;

@Service
public class UserServiceImpl implements UserService{

	UserRepository userRep;
	Pagination pagination;
	
	@Override
	public Optional<User> createUser(User user) {
		return Optional.of(userRep.save(user));
	}

	@Override
	public Optional<User> getUserByUsername(String username) {
		return userRep.findById(username);
	}

	@Override
	public boolean deleteUserByUsername(String username) {
		Optional<User> retVal = userRep.findById(username);
		if(!retVal.isPresent()) {
			userRep.deleteById(username);
			return true;
		}
		return false;
	}

	@Override
	public Optional<Page<User>> getUsers(int offset, int limit) {
		Pageable userPage = Pagination.of(offset, limit);
		return Optional.ofNullable(userRep.findAll(userPage));
	}

	@Override
	public Optional<User> updateUser(User user) {
		return Optional.of(userRep.save(user));
	}

}
