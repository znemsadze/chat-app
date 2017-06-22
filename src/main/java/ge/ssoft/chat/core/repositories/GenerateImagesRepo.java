package ge.ssoft.chat.core.repositories;

import ge.ssoft.chat.core.model.GenerateImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by zviad on 6/22/17.
 * generate images repo
 */

@Repository
public interface GenerateImagesRepo extends JpaRepository<GenerateImages,String> {


}
