package ge.ssoft.chat.core.repositories;

import ge.ssoft.chat.core.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

	Users findByUsername(String username);

	List<Users> findByParams(String lastName, String firstName, String userName);

}
