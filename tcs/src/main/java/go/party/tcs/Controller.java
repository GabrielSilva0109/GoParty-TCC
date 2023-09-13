package go.party.tcs;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
public class Controller {
    
    @GetMapping("/home")
    public String homePage(){
        return "home";
    }
}
