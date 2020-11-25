package ro.andreistoian.SpringMusicPlayer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ro.andreistoian.SpringMusicPlayer.models.FileDB;
import ro.andreistoian.SpringMusicPlayer.models.ResponseMessage;
import ro.andreistoian.SpringMusicPlayer.models.User;
import ro.andreistoian.SpringMusicPlayer.service.FileDBService;
import ro.andreistoian.SpringMusicPlayer.service.PlaylistService;
import ro.andreistoian.SpringMusicPlayer.service.UserService;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;



@Controller
@Slf4j
@CrossOrigin
public class AppController {

    @Autowired
    FileDBService service;


    @GetMapping("/main")
    public String main(Model model){
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




    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id, HttpServletResponse response) {
        //FileDB fileDB = service.getFile(id);
        byte[] data = service.getData(id);

        response.setContentType("audio/mp3");
        response.setContentLength(data.length);

        try {
            OutputStream os = response.getOutputStream();
            os.write(data);
        } catch (IOException e) {
           // e.printStackTrace();
            log.info("Aborted connection");
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/files_img/{id}/image.jpeg")
    public ResponseEntity<byte[]> getFilesImg(@PathVariable String id, HttpServletResponse response) {
        byte[] albumArt = service.getAlbumArt(id);
        response.setContentType("image/jpeg");
        response.setContentLength(albumArt.length);
        try {
            response.getOutputStream().write(albumArt);
        } catch (IOException e) {
        }

        return ResponseEntity.ok().build();
    }

}
