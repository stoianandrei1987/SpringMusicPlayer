package ro.andreistoian.SpringMusicPlayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.andreistoian.SpringMusicPlayer.models.Playlist;
import ro.andreistoian.SpringMusicPlayer.models.User;
import ro.andreistoian.SpringMusicPlayer.repository.PlaylistRepository;

@Service
public class PlaylistService {

    @Autowired
    PlaylistRepository repo;

    public void save(Playlist playlist) {
        repo.save(playlist);
    }

    public void saveFirst(User user){
        Playlist p = new Playlist("first", "Andrei's first playlist", 2020, user);
        repo.save(p);
    }

}
