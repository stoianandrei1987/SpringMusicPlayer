package ro.andreistoian.SpringMusicPlayer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.andreistoian.SpringMusicPlayer.models.FileDB;
import ro.andreistoian.SpringMusicPlayer.models.FileResponse;
import ro.andreistoian.SpringMusicPlayer.repository.FileDBRepository;
import ro.andreistoian.SpringMusicPlayer.repository.PlaylistRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
//@Transactional(readOnly = true, noRollbackFor = {SQLNonTransientConnectionException.class, GenericJDBCException.class})
@Qualifier("fileDBService")

public class FileDBService {

    @Autowired
    FileDBRepository repo;

    @Autowired
    PlaylistRepository pRepo;

    @Value("${andrei.this-ip}")
    private String MY_IP;

    @Value("${andrei.run-on-docker}")
    boolean RUN_ON_DOCKER;

    @Value("${andrei.port-mapped-to-container}")
    private String PORT;

    private final String VISUALIZATION = "michaelbromley_visualization";

    public void store(FileDB db) {
        repo.save(db);
    }

    public List<FileDB> getFiles() {
        return repo.findAll();
    }

    ;

    public FileDB getFile(String uid) {
        return repo.getOne(uid);
    }

    public byte[] getData(String uid) {
        return repo.getOne(uid).getData();
    }

    public byte[] getAlbumArt(String id) {
        return repo.getOne(id).getArt();
    }

    public List<FileResponse> getJson(String s) {


        Stream<FileDB> stream;
        if (s.equals("")) {
            stream = repo.findAll().stream();

        } else {
            stream = pRepo.findById(s).get().getSongs().stream();
        }

        return stream.map(entry -> {

            String fileUri, imgUri;

            if (!RUN_ON_DOCKER) {



                fileUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()

                        .path("/files/")
                        .path(entry.getId())
                        .toUriString();


                imgUri = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                       // .fromPath("http://" + MY_IP)
                        .path("/files_img/")
                        .path(entry.getId())
                        .toUriString() + "/image.jpeg";

            } else {
                fileUri = "http://" + MY_IP + ':' + PORT + "/files/" + entry.getId();
                imgUri = "http://" + MY_IP + ':' + PORT + "/files_img/" + entry.getId() + "/image.jpeg";


            }

            if (entry.getName().contains("-")) {
                String artist = entry.getName().split("-")[0].strip();
                String track = entry.getName().split("-")[1].strip();
                return new FileResponse(track, artist, entry.getAlbum(), fileUri, imgUri, VISUALIZATION);
            } else return new FileResponse(entry.getName(), "", entry.getAlbum(), fileUri, imgUri, VISUALIZATION);
        }).collect(Collectors.toList());


    }
}
