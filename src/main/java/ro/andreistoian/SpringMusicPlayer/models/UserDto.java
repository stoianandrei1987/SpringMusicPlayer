package ro.andreistoian.SpringMusicPlayer.models;

import lombok.Data;
import ro.andreistoian.SpringMusicPlayer.validation.PasswordMatches;
import ro.andreistoian.SpringMusicPlayer.validation.ValidEmail;

import javax.validation.constraints.*;

@Data
@PasswordMatches
public class UserDto {

    @NotNull
    @NotEmpty
    private String userName;

    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @PositiveOrZero
    @Max(value = 120)
    private Integer age;

}
