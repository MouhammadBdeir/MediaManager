package oniline.itlogic.mediamanager.model;



import javax.persistence.*;


@Entity
public class Bewertung {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "film_serien_id", nullable = false)
    private Media media;
    @ManyToOne
    @JoinColumn(name = "benutzerprofil_id", nullable = false)
    private Benutzerprofil benutzerprofil;

    private int bewertungswert;
    @Column(columnDefinition = "LONGTEXT")
    private String kommentar;

    public Bewertung(Long id, Media media, Benutzerprofil benutzerprofil, int bewertungswert, String kommentar) {
        this.id = id;
        this.media = media;
        this.benutzerprofil = benutzerprofil;
        this.bewertungswert = bewertungswert;
        this.kommentar = kommentar;
    }
    public Bewertung(Media media, Benutzerprofil benutzerprofil, int bewertungswert, String kommentar) {
        this.media = media;
        this.benutzerprofil = benutzerprofil;
        this.bewertungswert = bewertungswert;
        this.kommentar = kommentar;
    }
    public Bewertung(int bewertungswert, String kommentar) {

        this.bewertungswert = bewertungswert;
        this.kommentar = kommentar;
    }
    public Bewertung() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Media getFilmSerien() {
        return media;
    }

    public void setFilmSerien(Media media) {
        this.media = media;
    }

    public Benutzerprofil getBenutzerprofil() {
        return benutzerprofil;
    }

    public void setBenutzerprofil(Benutzerprofil benutzerprofil) {
        this.benutzerprofil = benutzerprofil;
    }

    public int getBewertungswert() {
        return bewertungswert;
    }

    public void setBewertungswert(int bewertungswert) {
        this.bewertungswert = bewertungswert;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}
