package ro.andreistoian.SpringMusicPlayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.andreistoian.SpringMusicPlayer.models.Playlist;
import ro.andreistoian.SpringMusicPlayer.models.User;
import ro.andreistoian.SpringMusicPlayer.repository.PlaylistRepository;


import java.util.Random;

@Service
//@Transactional(readOnly = true, noRollbackFor = {SQLNonTransientConnectionException.class, GenericJDBCException.class})
public class PlaylistService {

    @Autowired
    PlaylistRepository repo;
    

    public Playlist getPlaylistById(String pid) {
        return repo.findById(pid).get();
    }

    public void save(Playlist playlist) {
        repo.save(playlist);
    }

    public void saveFirst(User user){
        Playlist p = new Playlist("first", "Andrei's first playlist", 2020, user);
        repo.save(p);
    }

    public void createPlaylist(User user, String playlistName, String description, Integer year) {
        repo.save(new Playlist(playlistName, description, year, user));
    }

    public void createRandomPlaylist(User user) {

        int r = new Random().nextInt();
        repo.save(new Playlist("random playlist "+r, "random playlist "+r,
                2020, user));
    }



}
