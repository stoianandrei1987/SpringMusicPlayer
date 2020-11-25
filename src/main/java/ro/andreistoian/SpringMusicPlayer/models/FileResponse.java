package ro.andreistoian.SpringMusicPlayer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponse {

    @JsonProperty("name")
    String name;
    @JsonProperty("artist")
    String artist;
    @JsonProperty("album")
    String album;
    @JsonProperty("url")
    String url;
    @JsonProperty("cover_art_url")
    String coverArtUrl;
    @JsonProperty("visualization")
    String visualization;
}
