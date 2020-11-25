package ro.andreistoian.SpringMusicPlayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ro.andreistoian.SpringMusicPlayer.models.FileResponse;
import ro.andreistoian.SpringMusicPlayer.models.User;
import ro.andreistoian.SpringMusicPlayer.service.FileDBService;
import ro.andreistoian.SpringMusicPlayer.service.PlaylistService;
import ro.andreistoian.SpringMusicPlayer.service.UserService;

import java.util.List;

@RestController
@CrossOrigin
public class AppRestController {

    @Autowired
    FileDBService service;
    @Autowired
    PlaylistService playlistService;
    @Autowired
    UserService userService;

    @GetMapping(value = "/getjsonarray", produces = "application/json")
    @ResponseBody
    public List<FileResponse> getJson() {
        return service.getJson();

      /*
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            return mapper.writeValueAsString(service.getJson());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";

       */
    }

    @GetMapping("/createstuff")
    public void createStuff(){
        User u = userService.saveAndrei();
        playlistService.saveFirst(u);
    }
}
