package ge.ssoft.chat.core.repositories;
import ge.ssoft.chat.core.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepo extends JpaRepository<Gender, Integer> {

}
