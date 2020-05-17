package b13.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import b13.dto.User;

public interface UserRepository extends JpaRepository<User, String> {

}
