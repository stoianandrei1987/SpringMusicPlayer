package ro.andreistoian.SpringMusicPlayer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import ro.andreistoian.SpringMusicPlayer.service.FileDBService;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class PlayListResponse {


    @JsonProperty("playlist_id")
    String id;
    @JsonProperty("name")
    String name;
    @JsonProperty("description")
    String description;
    @JsonProperty("year")
    Integer year;
    @JsonProperty("created_at")
    LocalDateTime createdAt;

    @JsonProperty("files")
    List<FileResponse> fileResponses;

    public PlayListResponse(Playlist p, FileDBService service) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.year = p.getYear();
        this.createdAt = p.getCreatedAt();
        this.fileResponses = service.getJson(p.getId());
    }
}
