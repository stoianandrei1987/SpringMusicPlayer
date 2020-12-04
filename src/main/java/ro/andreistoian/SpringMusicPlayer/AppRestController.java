package ro.andreistoian.SpringMusicPlayer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.andreistoian.SpringMusicPlayer.models.*;
import ro.andreistoian.SpringMusicPlayer.service.FileDBService;
import ro.andreistoian.SpringMusicPlayer.service.PlaylistService;
import ro.andreistoian.SpringMusicPlayer.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@Slf4j
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
        return service.getJson("");
    }

    @GetMapping(value = "/getplaylist", produces = "application/json")
    @ResponseBody
    public List<PlayListResponse> getJson(@RequestParam(name = "uid") String uid) {

        log.info("Printing playlits : ");
        userService.getById(uid).getPlaylists().forEach(p -> log.info("Printing pl id : " + p.getId()));


        // return userService.getById(uid).getPlaylists().
        //       stream().map(p-> new PlayListResponse(p)).collect(Collectors.toList());
        List<Playlist> playlists = userService.getById(uid).getPlaylists();
        List<PlayListResponse> playListResponses = new ArrayList<>();
        playlists.forEach(p -> playListResponses.add(new PlayListResponse(p, service)));
        return playListResponses;

    }

    @GetMapping(value = "/listsongsfrompl", produces = {"application/json"})
    public @ResponseBody
    List<FileResponse> testGetSongsFromPl(@RequestParam(name = "pid") String pid) {
        Playlist p = playlistService.getPlaylistById(pid);
        return service.getJson(p.getId());


    }

    @GetMapping("/createnewplaylistforandrei")
    public void createNewPlaylist(HttpServletResponse response) {
        User u = userService.getById("2f9c64e3-2bd9-4849-96a9-74a0e854a694");
        playlistService.createRandomPlaylist(u);
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            response.getWriter().write("created random playlist");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/createplaylist")
    public void createPlaylist(HttpServletResponse response, @RequestParam(name = "n") String name,
                               @RequestParam(name = "d") String description, @RequestParam(name = "y") Integer year,
                               Principal principal) {

        playlistService.createPlaylist(userService.getUserByUserName(principal.getName()), name, description, year);

        try {
            response.getWriter().write("created playlist using parameters");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/createstuff")
    public void createStuff() {
        User u = userService.saveAndrei();
        playlistService.saveFirst(u);
    }
}
