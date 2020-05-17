package b13.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import b13.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
