package oniline.itlogic.mediamanager.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import oniline.itlogic.mediamanager.model.Media;
import oniline.itlogic.mediamanager.model.Movie;
import oniline.itlogic.mediamanager.service.BewertungService;
import oniline.itlogic.mediamanager.service.FilmSerienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FilmSerieController {
    private final FilmSerienService filmSerienService;
    private final BewertungService bewertungService;
    @Autowired
    public FilmSerieController(FilmSerienService filmSerienService,BewertungService bewertungService) {
        this.bewertungService=bewertungService;
        this.filmSerienService = filmSerienService;
    }
    @GetMapping("/v1/registration/media/all")
    public ResponseEntity<List<Media>> getAllFilmSerien() {
        List<Media> media = filmSerienService.findAllFilmSerien();
        return new ResponseEntity<>(media, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Media> getFilmSerienById(@PathVariable("id") Long id){
        Media media = filmSerienService.findFilmSerienById(id);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Media> addFilmSerien(@RequestBody Media media) {
        Movie m =getMovieInfo(media.getTitel());
        if(m!=null) {
            media.setVeroeffentlichungsjahr(m.getRelease_date());
            media.setBeschreibung(m.getOverview());
            Media newMedia = filmSerienService.addFilmSerien(media);
            return new ResponseEntity<>(newMedia, HttpStatus.CREATED);
        }else{
            System.err.println("Moive wurd nicht gefunden");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/update")
    public ResponseEntity<Media> updateFilmSerien(@RequestBody Media media){
        Media updatedMedia = filmSerienService.updateFilmSerien(media);
        return new ResponseEntity<>(updatedMedia, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFilmSerien(@PathVariable("id") Long id){
        /*
        for(Bewertung bewertung : bewertungService.findAllBewertungen()){
            bewertungService.deleteBewertungen(bewertung.getId());
        }*/
        filmSerienService.deleteFilmSerien(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    public Movie getMovieInfo(String movieName) {
        Movie movie = null;
        try {
            String apiUrl = "https://api.themoviedb.org/3/search/movie";
            String query = "?api_key=" +"c30ab17417834ee8abfef339e2f7e20b"+ "&query=" + movieName;

            URL url = new URL(apiUrl + query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());
            JsonNode resultsNode = rootNode.get("results");

            if (resultsNode.isArray()) {
                for (JsonNode movieNode : resultsNode) {
                    String title = movieNode.get("title").asText();

                    if (title.equalsIgnoreCase(movieName)) {
                        movie = objectMapper.readValue(movieNode.toString(), Movie.class);
                        System.out.println("Title: " + movie.getTitle());
                        System.out.println("Overview: " + movie.getOverview());
                        System.out.println("Release Date: " + movie.getRelease_date());
                        System.out.println();
                        break;
                    }
                }
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

}
