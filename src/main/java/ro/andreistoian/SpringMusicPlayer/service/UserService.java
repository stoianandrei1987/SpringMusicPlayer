package ro.andreistoian.SpringMusicPlayer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.andreistoian.SpringMusicPlayer.models.User;
import ro.andreistoian.SpringMusicPlayer.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository repo;

    public void saveUser(User user){
        repo.save(user);
    }

    //pass for andrei is pass

    public User saveAndrei(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User("Andrei", "Stoian",
                "stoianandrei@yahoo.com", encoder.encode("pass"), 33);
        repo.save(user);
        return user;
    }

}
