package ro.andreistoian.SpringMusicPlayer.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "playlists")
public class Playlist {

    @Id
    @Column(name = "uid")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "year")
    Integer year;

    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_uid",referencedColumnName = "uuid", nullable = false)
    User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "songs_playlist",
            joinColumns = @JoinColumn(name = "playlist_uid"),
            inverseJoinColumns = @JoinColumn(name = "song_uid"))
    List<FileDB> songs;

    public Playlist(String name, String description, Integer year, User user) {
        this.name = name;
        this.description = description;
        this.year = year;
        this.user = user;

    }
}
