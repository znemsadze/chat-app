package ge.ssoft.chat.core.repositories;
import ge.ssoft.chat.core.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {

}
