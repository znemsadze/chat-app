package ge.ssoft.chat.mvc;

import ge.ssoft.chat.core.model.Gender;
import ge.ssoft.chat.core.repositories.GenderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by zviad on 6/25/17.
 * Controller for common dictionari key values
 */
@RestController
@RequestMapping(value = "dictionary")
public class CommonDictionaryController {

    private GenderRepo genderRepo;

    @Autowired
    public void setGenderRepo(GenderRepo genderRepo) {
        this.genderRepo = genderRepo;
    }
    @RequestMapping(value = "gender",method = RequestMethod.GET)
    List<Gender> getAllGender(){
      return  genderRepo.findAll();
    }


}
