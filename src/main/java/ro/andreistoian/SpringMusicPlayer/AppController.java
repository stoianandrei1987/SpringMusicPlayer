package ro.andreistoian.SpringMusicPlayer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ro.andreistoian.SpringMusicPlayer.models.*;
import ro.andreistoian.SpringMusicPlayer.service.FileDBService;
import ro.andreistoian.SpringMusicPlayer.service.PlaylistService;
import ro.andreistoian.SpringMusicPlayer.service.UserAlreadyExistException;
import ro.andreistoian.SpringMusicPlayer.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@Controller
@Slf4j
@CrossOrigin
public class AppController {

    @Autowired
    FileDBService service;

    @Autowired
    PlaylistService playlistService;

    @Autowired
    UserService userService;

    @GetMapping("/main")
    public String main(Model model) {
        return "main";
    }

    @PostMapping("/upload")
    @CrossOrigin
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("audio") MultipartFile audio,
                                                      @RequestParam("art") MultipartFile art,
                                                      @RequestParam("name") String name,
                                                      @RequestParam("album") String album,
                                                      @RequestParam("year") String year) {
        String message = "";
        try {

            FileDB file = new FileDB(name, album, Integer.valueOf(year), audio.getBytes(), art.getBytes());
            service.store(file);
            message = "Uploaded the file successfully: " + audio.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + audio.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/uploadtoplaylist")
    @CrossOrigin
    public ResponseEntity<ResponseMessage> uploadToPlaylist(@RequestParam("audio") MultipartFile audio,
                                                            @RequestParam("art") MultipartFile art,
                                                            @RequestParam("name") String name,
                                                            @RequestParam("album") String album,
                                                            @RequestParam("year") String year,
                                                            @RequestParam("playlist") String playlistId) {
        String message = "";
        try {

            FileDB file = new FileDB(name, album, Integer.valueOf(year), audio.getBytes(), art.getBytes());
            service.store(file);
            Playlist p = playlistService.getPlaylistById(playlistId);
            p.getSongs().add(file);
            playlistService.save(p);
            message = "Uploaded the file successfully (to playlist): " + audio.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + audio.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping("register")
    public String loadRegisterPage(Model model) {

        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register")
    @CrossOrigin
    public ModelAndView registerUserAccount
            (@ModelAttribute("user") @Valid UserDto userDto,
             HttpServletRequest request, Errors errors) {

        try {
            User registered = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException uaeEx) {
            ModelAndView mav = new ModelAndView("register-failure");
            mav.addObject("message", "An account for that username/email already exists.");
            return mav;
        }

        return new ModelAndView("register-success");
        // rest of the implementation
    }


    @GetMapping("/files/{id}")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<Resource> getFile(@PathVariable String id) {
        //FileDB fileDB = service.getFile(id);
        byte[] data = service.getData(id);
        /*
        response.setContentType("audio/mp3");
        response.setContentLength(data.length);
        try {
            OutputStream os = response.getOutputStream();
            os.write(data);
        } catch (IOException e) {
            // e.printStackTrace();
            log.info("Aborted connection");
        }

         */


        Resource r = new ByteArrayResource(data);
        return ResponseEntity.ok().contentType(new MediaType("audio", "mp3")).
                contentLength(data.length).body(r);


    }

    @GetMapping("/files_img/{id}/image.jpeg")
    public ResponseEntity<Resource> getFilesImg(@PathVariable String id, HttpServletResponse response) {
        byte[] albumArt = service.getAlbumArt(id);
        return ResponseEntity.ok().contentType(new MediaType("image", "jpeg")).
                contentLength(albumArt.length).body(new ByteArrayResource(albumArt));


    }

}
