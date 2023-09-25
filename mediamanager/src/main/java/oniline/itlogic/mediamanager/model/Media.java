package oniline.itlogic.mediamanager.model;


import oniline.itlogic.mediamanager.service.FilmSerienService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false )
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "media_categories",
            joinColumns = @JoinColumn(name = "media_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories= new ArrayList<>();;
    private String titel;
    private String genre;
    private String regisseur;
    private String veroeffentlichungsjahr;
    private String URLTrailer;
    private String imgSrc;
    @Column(columnDefinition = "LONGTEXT")
    private String beschreibung;
    @Column(nullable = false, updatable = false)
    private String mediaCode;

    public Media(Long id, String titel,String URLTrailer, String genre, String regisseur, String veroeffentlichungsjahr, String beschreibung, String mediaCode,String imgSrc) {
        this.id = id;
        this.URLTrailer=URLTrailer;
        this.titel = titel;
        this.genre = genre;
        this.regisseur = regisseur;
        this.veroeffentlichungsjahr = veroeffentlichungsjahr;
        this.beschreibung = beschreibung;
        this.mediaCode = mediaCode;
        this.imgSrc = imgSrc;

    }

    public Media(String titel, String genre, String regisseur, String URLTrailer,String veroeffentlichungsjahr, String beschreibung, String mediaCode,String imgSrc) {
        this.titel = titel;
        this.URLTrailer=URLTrailer;
        this.genre = genre;
        this.regisseur = regisseur;
        this.veroeffentlichungsjahr = veroeffentlichungsjahr;
        this.beschreibung = beschreibung;
        this.mediaCode = mediaCode;
        this.imgSrc = imgSrc;

    }

    public Media() {

    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getMediaCode() {
        return mediaCode;
    }

    public void setMediaCode(String mediaCode) {
        this.mediaCode = mediaCode;
    }

    public Long getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public String getGenre() {
        return genre;
    }

    public String getRegisseur() {
        return regisseur;
    }

    public String getVeroeffentlichungsjahr() {
        return veroeffentlichungsjahr;
    }

    public String getURLTrailer() {
        return URLTrailer;
    }

    public void setURLTrailer(String URLTrailer) {
        this.URLTrailer = URLTrailer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRegisseur(String regisseur) {
        this.regisseur = regisseur;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setVeroeffentlichungsjahr(String veröffentlichungsjahr) {
        this.veroeffentlichungsjahr = veröffentlichungsjahr;
    }

}
