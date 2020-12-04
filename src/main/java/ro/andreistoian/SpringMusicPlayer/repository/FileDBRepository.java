package ro.andreistoian.SpringMusicPlayer.repository;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.andreistoian.SpringMusicPlayer.models.FileDB;

import javax.persistence.PersistenceException;
import java.sql.SQLNonTransientConnectionException;

@Repository
@Transactional(readOnly = true, noRollbackFor = {SQLNonTransientConnectionException.class, GenericJDBCException.class})
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
