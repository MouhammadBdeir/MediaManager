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
    private int veroeffentlichungsjahr;
    @Column(nullable = false, updatable = false)
    private String mediaCode;

    public Media(String titel, String genre, String regisseur, int veroeffentlichungsjahr, String mediaCode) {
        this.titel = titel;
        this.genre = genre;
        this.regisseur = regisseur;
        this.veroeffentlichungsjahr = veroeffentlichungsjahr;
        this.mediaCode=mediaCode;
    }

    public Media(Long id, String titel, String genre, String regisseur, int veroeffentlichungsjahr, String mediaCode) {
        this.id = id;
        this.titel = titel;
        this.genre = genre;
        this.regisseur = regisseur;
        this.veroeffentlichungsjahr = veroeffentlichungsjahr;
        this.mediaCode=mediaCode;
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

    public int getVeroeffentlichungsjahr() {
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

    public void setVeroeffentlichungsjahr(int veröffentlichungsjahr) {
        this.veroeffentlichungsjahr = veröffentlichungsjahr;
    }
}
