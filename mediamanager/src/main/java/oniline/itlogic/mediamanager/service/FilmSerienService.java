package oniline.itlogic.mediamanager.service;

import oniline.itlogic.mediamanager.exceptions.UserNotFoundException;
import oniline.itlogic.mediamanager.model.Media;
import oniline.itlogic.mediamanager.repo.FilmSerienRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FilmSerienService {
    private final FilmSerienRepository filmSerienRepository;

    public FilmSerienService(FilmSerienRepository filmSerienRepository) {
        this.filmSerienRepository = filmSerienRepository;
    }
    public Media addFilmSerien(Media film){
        film.setMediaCode(UUID.randomUUID().toString());
        return  filmSerienRepository.save(film);
    }
    public List<Media> findAllFilmSerien(){
        return filmSerienRepository.findAll();
    }
    public Media updateFilmSerien(Media film){
        return filmSerienRepository.save(film);
    }
    public Media findFilmSerienById(Long id){
        return filmSerienRepository.findFilmSerienById(id).orElseThrow(()-> new UserNotFoundException("Film by id " + id+" was not Found" ));
    }
    public  void deleteFilmSerien(Long id){
        filmSerienRepository.deleteById(id);
    }
}