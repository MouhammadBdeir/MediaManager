package oniline.itlogic.mediamanager.controller;
import oniline.itlogic.mediamanager.model.Benutzerprofil;
import oniline.itlogic.mediamanager.model.Media;
import oniline.itlogic.mediamanager.service.BewertungService;
import oniline.itlogic.mediamanager.model.Bewertung;
import oniline.itlogic.mediamanager.service.FilmSerienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/bewertung")
public class BewertungenContraoller {
    private final BewertungService bewertungService;
    private final FilmSerienService filmSerienService;

    @Autowired
    public BewertungenContraoller(BewertungService bewertungService,FilmSerienService filmSerienService) {
        this.bewertungService = bewertungService;
        this.filmSerienService=filmSerienService;
    }
    @GetMapping("/allUser")
    public ResponseEntity<List<Bewertung>> getAllBewertungen(Authentication authentication){
        Benutzerprofil userDetails = (Benutzerprofil) authentication.getPrincipal();

        List<Bewertung> allBewertungen = bewertungService.findAllBewertungen();
        List<Bewertung> bewertungenDesBenutzers = new ArrayList<>();

        for (Bewertung bewertung : allBewertungen) {
            if (bewertung.getBenutzerprofil().getId()==userDetails.getId()) {
                bewertungenDesBenutzers.add(bewertung);
            }
        }
        return new ResponseEntity<>(bewertungenDesBenutzers, HttpStatus.OK);
    }
    @GetMapping("/allMedia/{id}")
    public ResponseEntity<List<Bewertung>> getAllBewertungenForMedia(@PathVariable("id") Long id){
        Media media = filmSerienService.findFilmSerienById(id);
        List<Bewertung> allBewertungen = bewertungService.findAllBewertungen();
        List<Bewertung> bewertungenDesBenutzers = new ArrayList<>();
        for (Bewertung bewertung : allBewertungen) {
            if (bewertung.getFilmSerien().getId()== media.getId()) {
                bewertungenDesBenutzers.add(bewertung);
            }
        }
        return new ResponseEntity<>(bewertungenDesBenutzers, HttpStatus.OK);
    }
    @GetMapping("/all")
    public ResponseEntity<List<Bewertung>> getAllBewertungenForMedia(){
        List<Bewertung> allBewertungen = bewertungService.findAllBewertungen();
        System.out.println("show comments");
        return new ResponseEntity<>(allBewertungen, HttpStatus.OK);
    }
    @GetMapping("/find/{id}")
    public ResponseEntity<Bewertung> getBewertungenById(@PathVariable("id") Long id){
        Bewertung bewertungen= bewertungService.findBewertungenById(id);
        return new ResponseEntity<>(bewertungen, HttpStatus.OK);
    }
    @PostMapping("/add/{id}")
    public ResponseEntity<Bewertung> addBewertungen(@RequestBody Bewertung bewertungen,@PathVariable("id") Long id ,Authentication authentication){
        Benutzerprofil userDetails = (Benutzerprofil) authentication.getPrincipal();
        Media media = filmSerienService.findFilmSerienById(id);
        bewertungen.setBenutzerprofil(userDetails);
        bewertungen.setFilmSerien(media);
        Bewertung newBewertungen = bewertungService.addBewertungen(bewertungen);
        System.out.println("komment hinzugefügt");
        return new ResponseEntity<>(newBewertungen, HttpStatus.CREATED);

    }
    @PutMapping("/update")
    public ResponseEntity<Bewertung> updateBewertungen(@RequestBody Bewertung bewertungen){
        Bewertung updatedBewertungen = bewertungService.updateBewertungen(bewertungen);
        return new ResponseEntity<>(updatedBewertungen, HttpStatus.OK);

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBewertungen(@PathVariable("id") Long id){
        bewertungService.deleteBewertungen(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
