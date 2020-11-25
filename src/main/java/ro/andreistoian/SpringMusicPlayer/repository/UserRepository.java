package ro.andreistoian.SpringMusicPlayer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.andreistoian.SpringMusicPlayer.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
