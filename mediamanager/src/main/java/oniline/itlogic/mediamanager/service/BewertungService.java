package oniline.itlogic.mediamanager.service;

import oniline.itlogic.mediamanager.exceptions.UserNotFoundException;
import oniline.itlogic.mediamanager.model.Bewertung;
import oniline.itlogic.mediamanager.repo.BewertungenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BewertungService {
    private final BewertungenRepository bewertungRepository;

    public BewertungService(BewertungenRepository bewertungenRepository) {
        this.bewertungRepository = bewertungenRepository;
    }

    public Bewertung addBewertungen(Bewertung bewertungener){
        return  bewertungRepository.save(bewertungener);
    }
    public List<Bewertung> findAllBewertungen(){
        return bewertungRepository.findAll();
    }

    public Bewertung updateBewertungen(Bewertung bewertungen){
        return bewertungRepository.save(bewertungen);
    }
    public Bewertung findBewertungenById(Long id){
        return bewertungRepository.findBewertungenById(id).orElseThrow(()-> new UserNotFoundException("Bewertung by id " + id+" was not Found" ));
    }
    public  void deleteBewertungen(Long id){
        bewertungRepository.deleteById(id);
    }
}