package ro.andreistoian.SpringMusicPlayer.service;



public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String s){
        super(s);
    };

}
