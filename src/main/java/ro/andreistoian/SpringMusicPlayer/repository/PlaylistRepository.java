package ro.andreistoian.SpringMusicPlayer.repository;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.andreistoian.SpringMusicPlayer.models.Playlist;

import javax.persistence.PersistenceException;
import java.sql.SQLNonTransientConnectionException;

@Repository
@Transactional(readOnly = true, noRollbackFor = {SQLNonTransientConnectionException.class, GenericJDBCException.class})
public interface PlaylistRepository extends JpaRepository<Playlist, String> {
}
