package ro.andreistoian.SpringMusicPlayer.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "songs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDB {

    @Id
    @Column(name = "uid")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    String id;

    @Column(name = "name")
    private String name;

    @Column(name = "album")
    private String album;

    @Column(name = "year")
    private Integer year;

    @Lob
    @Column(name = "data", length = 40000000)
    byte[] data;

    @Lob
    @Column(name = "art", length = 40000000)
    byte[] art;

    @ManyToMany(mappedBy = "songs")
    List<Playlist> playlists;



    public FileDB(String name, String album, Integer year, byte[] data, byte[] art) {
        this.name = name;
        this.album = album;
        this.year = year;
        this.data = data;
        this.art = art;
    }
}
