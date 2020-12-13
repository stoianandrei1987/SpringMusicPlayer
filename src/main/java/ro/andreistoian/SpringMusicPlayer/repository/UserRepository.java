package ro.andreistoian.SpringMusicPlayer.repository;

import org.hibernate.exception.GenericJDBCException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.andreistoian.SpringMusicPlayer.models.User;

import javax.persistence.PersistenceException;
import java.sql.SQLNonTransientConnectionException;
import java.util.List;
import java.util.Optional;

@Repository
//@Transactional(readOnly = false, noRollbackFor = {SQLNonTransientConnectionException.class, GenericJDBCException.class})
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUserName(String s);
    @Query(
            value = "SELECT * FROM USERS u WHERE u.email = :email",
            nativeQuery = true)
    List<User> findByEmail(@Param("email") String email);
}
