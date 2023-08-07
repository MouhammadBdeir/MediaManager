package oniline.itlogic.mediamanager.controller;

import oniline.itlogic.mediamanager.model.Bewertung;
import oniline.itlogic.mediamanager.model.Media;
import oniline.itlogic.mediamanager.service.BewertungService;
import oniline.itlogic.mediamanager.service.FilmSerienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Media>> getAllFilmSerien(){
        List<Media> media = filmSerienService.findAllFilmSerien();
        return new ResponseEntity<>(media, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Media> getFilmSerienById(@PathVariable("id") Long id){
        Media media = filmSerienService.findFilmSerienById(id);
        return new ResponseEntity<>(media, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Media> addFilmSerien(@RequestBody Media media){
        Media newMedia = filmSerienService.addFilmSerien(media);
        return new ResponseEntity<>(newMedia, HttpStatus.CREATED);

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
}
