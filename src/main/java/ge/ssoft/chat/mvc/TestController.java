package ge.ssoft.chat.mvc;

import org.springframework.web.bind.annotation.*;

/**
 * Created by zviad on 6/9/17.
 * test Controller class
 */
@RestController
@RequestMapping(value = "test")
public class TestController {


    @RequestMapping(value = "/{something}" ,method = RequestMethod.GET)
    public String saySomething(@PathVariable String something){
        return "servers said "+ something;

    }


}
