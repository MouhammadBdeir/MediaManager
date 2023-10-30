package oniline.itlogic.mediamanager.controller;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import oniline.itlogic.mediamanager.model.Category;
import oniline.itlogic.mediamanager.model.Media;
import oniline.itlogic.mediamanager.model.Movie;
import oniline.itlogic.mediamanager.service.BewertungService;
import oniline.itlogic.mediamanager.service.CategoryService;
import oniline.itlogic.mediamanager.service.FilmSerienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.desktop.SystemSleepListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class FilmSerieController {
    private final FilmSerienService filmSerienService;
    private final CategoryService categoryService;

    @Autowired
    public FilmSerieController(FilmSerienService filmSerienService,CategoryService categoryService) {

        this.filmSerienService = filmSerienService;
        this.categoryService=categoryService;
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
        Movie m =getMovieInfoWithTrailer(media.getTitel());
        if(m!=null) {
            media.setVeroeffentlichungsjahr(m.getRelease_date());
            media.setBeschreibung(m.getOverview());
            media.setURLTrailer(m.getURLTrailer());
            media.setImgSrc(m.getImgSrc());
            Media newMedia = filmSerienService.addFilmSerien(media);

            return new ResponseEntity<>(newMedia, HttpStatus.CREATED);
        }else{
            System.err.println("Moive wurd nicht gefunden");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/categories/add/{id}/{cat}")
    public ResponseEntity<Media> addCategories(
            @PathVariable("id") Long id,
            @PathVariable("cat") String categoryString
    ) {
        Media media = filmSerienService.findFilmSerienById(id);
        Category category = new Category(categoryString);
        categoryService.addCategory(category);
        List<Category> categories = media.getCategories();
        categories.add(category);
        Media updatedMedia = filmSerienService.updateFilmSerien(media);
        return ResponseEntity.ok(updatedMedia);
    }
    @PostMapping("/categories/delete/{idMedia}/{idCat}")
    public ResponseEntity<?> deleteCategori(
            @PathVariable("idMedia") Long idMedia,
            @PathVariable("idCat") Long idCat){
        Media media = filmSerienService.findFilmSerienById(idMedia);
        List<Category> categories = media.getCategories();
        List<Category>newCat=new ArrayList<>();
        for(Category cat: categories){
            if (!Objects.equals(cat.getId(), idCat)){
                newCat.add(cat);
            }
        }
        media.setCategories(newCat);
        Media updatedMedia = filmSerienService.updateFilmSerien(media);
        return ResponseEntity.ok(updatedMedia);
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
    public  Movie getMovieInfoWithTrailer(String movieName) {
        Movie movie = null;
        try {
            String apiUrl = "https://api.themoviedb.org/3/search/movie";
            String encodedMovieName = URLEncoder.encode(movieName, "UTF-8");
            String apiKey = "c30ab17417834ee8abfef339e2f7e20b";
            String query = "?api_key=" + apiKey + "&query=" + encodedMovieName;

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
                String searchQueryLowerCase = movieName.toLowerCase();

                for (JsonNode movieNode : resultsNode) {
                    String title = movieNode.get("title").asText().toLowerCase();

                    if (title.contains(searchQueryLowerCase)) {
                        String movieId = movieNode.get("id").asText(); // Extrahiere die movieId
                        movie = objectMapper.readValue(movieNode.toString(), Movie.class);
                        System.out.println("Title: " + movie.getTitle());
                        System.out.println("Overview: " + movie.getOverview());
                        System.out.println("Release Date: " + movie.getRelease_date());

                        // Fetch movie trailer
                        movie = getMovieTrailerUrl(movieId, apiKey , movie );
                        System.out.println("Trailer URL: " + movie.getURLTrailer());
                        System.out.println("Picture SRC: " + movie.getImgSrc());
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
    public  Movie getMovieTrailerUrl(String movieId, String apiKey ,Movie movie) {
        try {
            String apiUrl = "https://api.themoviedb.org/3/movie/" + movieId + "/videos";
            String query = "?api_key=" + apiKey;

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

            if (resultsNode.isArray() && resultsNode.size() > 0) {
                String key = resultsNode.get(0).get("key").asText();
                String path = "https://img.youtube.com/vi/" + key + "/0.jpg";
                movie.setImgSrc(path);
                movie.setURLTrailer("https://www.youtube.com/embed/" + key+"?showinfo=0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

}
