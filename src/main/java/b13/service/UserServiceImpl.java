package b13.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import b13.dao.Pager;
import b13.dao.UserRepository;
import b13.dao.util.Pagination;
import b13.dto.User;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{
	static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
	
	UserRepository userRep;
	
	@Override
	public Optional<User> createUser(User user) {
		Optional<User> retVal = Optional.of(userRep.save(user));
		logger.severe("umm...");
		return retVal;
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
		return Optional.ofNullable(userRep.findAll(Pager.of(offset, limit)));
	}

	@Override
	public Optional<User> updateUser(User user) {
		return Optional.of(userRep.save(user));
	}

}
