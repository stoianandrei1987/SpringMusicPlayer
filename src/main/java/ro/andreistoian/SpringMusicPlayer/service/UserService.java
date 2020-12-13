package ro.andreistoian.SpringMusicPlayer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.andreistoian.SpringMusicPlayer.models.User;
import ro.andreistoian.SpringMusicPlayer.models.UserDto;
import ro.andreistoian.SpringMusicPlayer.repository.UserRepository;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository repo;

    public void saveUser(User user) {
        repo.save(user);
    }

    public String getUidByUserName(String name) {
        return repo.findByUserName(name).get().getId();
    }

    //pass for andrei is pass

    public User getById(String uid) {
        log.info("Trying to get user with ID : " + uid);
        User user = repo.getOne(uid);
        if (user != null) {
            log.info("found user with f_name : " + user.getFirstName());
        } else log.info("did not find user");
        return user;
    }

    public User getUserByUserName(String name) {
        return repo.findByUserName(name).get();
    }

    public User saveAndrei() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User("Andrei", "Stoian",
                "stoianandrei@yahoo.com", encoder.encode("pass"), 33);
        repo.save(user);
        return user;
    }

    @Transactional
    public User registerNewUserAccount(UserDto userDto)
            throws UserAlreadyExistException {

        User registered;

        if (emailExist(userDto.getEmail()) || usernameExists(userDto.getUserName())) {
            throw new UserAlreadyExistException(
                    "There is an account with that email address: "
                            + userDto.getEmail() + " and this username : " + userDto.getUserName());
        } else {
            registered = new User(userDto.getUserName(), userDto.getFirstName(),
                    userDto.getLastName(), userDto.getEmail(), new BCryptPasswordEncoder().encode(userDto.
                    getPassword()), userDto.getAge());
            repo.save(registered);

        }

        return registered;

    }

    private boolean emailExist(String email) {
        return !repo.findByEmail(email).isEmpty();
    }

    private boolean usernameExists(String userName) {
        return !repo.findByUserName(userName).isEmpty();
    }

}
