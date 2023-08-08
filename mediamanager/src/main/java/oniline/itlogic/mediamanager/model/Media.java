package oniline.itlogic.mediamanager.model;


import javax.persistence.*;

@Entity
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false )
    private Long id;
    private String titel;
    private String genre;
    private String regisseur;
    private String veroeffentlichungsjahr;
    @Column(columnDefinition = "LONGTEXT")
    private String beschreibung;
    @Column(nullable = false, updatable = false)
    private String mediaCode;

    public Media(Long id, String titel, String genre, String regisseur, String veroeffentlichungsjahr, String beschreibung, String mediaCode) {
        this.id = id;
        this.titel = titel;
        this.genre = genre;
        this.regisseur = regisseur;
        this.veroeffentlichungsjahr = veroeffentlichungsjahr;
        this.beschreibung = beschreibung;
        this.mediaCode = mediaCode;
    }

    public Media(String titel, String genre, String regisseur, String veroeffentlichungsjahr, String beschreibung, String mediaCode) {
        this.titel = titel;
        this.genre = genre;
        this.regisseur = regisseur;
        this.veroeffentlichungsjahr = veroeffentlichungsjahr;
        this.beschreibung = beschreibung;
        this.mediaCode = mediaCode;
    }

    public Media() {

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
