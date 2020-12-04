package ro.andreistoian.SpringMusicPlayer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "playlists")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Playlist {

    @Id
    @Column(name = "uid")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "year")
    private Integer year;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_uid",referencedColumnName = "uuid", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "songs_playlist",
            joinColumns = @JoinColumn(name = "playlist_uid"),
            inverseJoinColumns = @JoinColumn(name = "song_uid"))
    private List<FileDB> songs;

    public Playlist(String name, String description, Integer year, User user) {
        this.name = name;
        this.description = description;
        this.year = year;
        this.user = user;

    }
}
