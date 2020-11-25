package ro.andreistoian.SpringMusicPlayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.andreistoian.SpringMusicPlayer.models.FileDB;

import javax.persistence.PersistenceException;
import java.sql.SQLNonTransientConnectionException;

@Repository
@Transactional(readOnly = true, noRollbackFor = {SQLNonTransientConnectionException.class, PersistenceException.class})
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
