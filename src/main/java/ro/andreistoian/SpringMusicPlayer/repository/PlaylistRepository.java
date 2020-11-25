package ro.andreistoian.SpringMusicPlayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.andreistoian.SpringMusicPlayer.models.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
}
