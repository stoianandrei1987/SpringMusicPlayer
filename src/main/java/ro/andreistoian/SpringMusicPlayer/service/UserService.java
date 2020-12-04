package ro.andreistoian.SpringMusicPlayer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.andreistoian.SpringMusicPlayer.models.User;
import ro.andreistoian.SpringMusicPlayer.repository.UserRepository;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository repo;

    public void saveUser(User user){
        repo.save(user);
    }

    public String getUidByUserName(String name) {
        return repo.findByUserName(name).get().getId();
    }

    //pass for andrei is pass

    public User getById(String uid) {
        log.info("Trying to get user with ID : " + uid);
        User user =  repo.getOne(uid);
        if(user!=null) {
            log.info("found user with f_name : " + user.getFirstName());
        }
        else log.info("did not find user");
        return user;
    }

    public User getUserByUserName(String name) {return repo.findByUserName(name).get();}

    public User saveAndrei(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User("Andrei", "Stoian",
                "stoianandrei@yahoo.com", encoder.encode("pass"), 33);
        repo.save(user);
        return user;
    }

}
