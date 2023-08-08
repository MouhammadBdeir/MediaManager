import { Component, OnInit } from '@angular/core';
import { FilmSerien } from './media';
import { MediaService } from './media.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ViewChild, ElementRef } from '@angular/core';
import { BenutzerService } from '../user/benutzer.service';
import { Benutzerprofile } from '../user/benutzer';
import {AuthService} from '../login/LoginService ';
@Component({
  selector: 'app-media',
  templateUrl: './media.component.html',
  styleUrls: ['./media.component.css']
})

export class MediaComponent implements OnInit {
  successMessage: string | null = null;
  errorMessage: string | null = null;
  @ViewChild('editModalElement')
  editModalElement!: ElementRef;
  @ViewChild('editBnutzerModalElement')
  editBnutzerModalElement!: ElementRef;
  @ViewChild('createModalElement')
  createModalElement!: ElementRef;
  medien: FilmSerien[] | undefined;
  benutzerprofile: Benutzerprofile[]=[];
  editMediaData: FilmSerien = {
    id: 0,
    titel: '',
    beschreibung: '',
    veroeffentlichungsjahr: '',
  };
  editBenutzerData: Benutzerprofile = {
    id: 0,
    benutzername: '',
    password: '',
    email: '',
  };
  newMediaData: FilmSerien = {
    id: 0,
    titel: '',
    beschreibung: '',
    veroeffentlichungsjahr: '',
  };
  constructor(private mediaService: MediaService,
    private formBuilder: FormBuilder,
    private benutzerService: BenutzerService,
    private authService: AuthService,
    private router: Router) { }
  ngOnInit() {
    if(this.authService.checkAuthStatus()&&this.authService.getCurrentUser().benutzerRole==='ADMIN'){
      this.getMedien();
      this.loadBenutzerprofile();
    }else{
      this.router.navigateByUrl('/login', );
    }
  }
  getMedien() {
    this.mediaService.getMedien().subscribe(
      (data: FilmSerien[]) => {
        this.medien = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }
  deleteMedia(mediaId: number) {
    this.mediaService.delete(mediaId).subscribe(
      () => {
        location.reload();
        this.showSuccessMessage("Movie erfolgreich gelöscht");
      },
      (error) => {
        this.showErrorMessage("Movie erfolgreich gelöscht"+error)
        console.log(error);

      }
    );
  }
  confirmDelete(mediaId: number) {
    if (confirm("Are you sure you want to delete this media?")) {
      this.deleteMedia(mediaId); 
    }
  }
  editMedia(media: FilmSerien) {

  }
  openEditModal(media: FilmSerien) {
    this.editMediaData = { ...media };
    this.editModalElement.nativeElement.style.display = "block";
  }
  closeEditModal() {
    this.editModalElement.nativeElement.style.display = "none";
  }
  saveEditMedia() {
   // Hier erfolgt die Logik zum Speichern der bearbeiteten Daten
  this.mediaService.update(this.editMediaData).subscribe(
    () => {
      // Erfolgreich aktualisiert, hier kannst du die entsprechende Aktion ausführen, wie z.B. Aktualisieren der Medienliste
      this.getMedien();
      this.showSuccessMessage("Speichern erfolgreich!")
      this.errorMessage = null;
    },
    (error) => {
      console.log(error);
      this.successMessage = null;
      this.showErrorMessage ("Fehler beim Speichern.");
    }
  );
    this.closeEditModal();
  }
  openCreateModal() {
    this.createModalElement.nativeElement.style.display = 'block';
  }
  closeCreateModal() {
    this.createModalElement.nativeElement.style.display = 'none';
  }
  createMedia() {
   // Hier erfolgt die Logik zum Speichern der bearbeiteten Daten
   this.mediaService.add(this.newMediaData).subscribe(
    () => {
      this.showSuccessMessage("Einfügen erfolgreich!")
      this.getMedien();
    },
    (error) => {
      this.showErrorMessage("Movie nicht gefunden")
      console.log("Movie wurde nicht  gefunden ",error);
    }
  );
    this.closeCreateModal();
  }
  loadBenutzerprofile() {
    this.benutzerService.getBenuztern().subscribe(
      (data: Benutzerprofile[]) => {
        this.benutzerprofile = data;
      },
      (error) => {
        console.log(error);
      }
    );
  }
  editBenutzer(benutzer: Benutzerprofile) {
    this.editBenutzerData = { ...benutzer };
    this.editBnutzerModalElement.nativeElement.style.display = "block";
  }
  saveEditBenutzer() {
    // Hier erfolgt die Logik zum Speichern der bearbeiteten Daten
   this.benutzerService.updateBenuzter(this.editBenutzerData).subscribe(
     () => {
       // Erfolgreich aktualisiert, hier kannst du die entsprechende Aktion ausführen, wie z.B. Aktualisieren der Medienliste
       this.getMedien();
     },
     (error) => {
       console.log(error);
     }
   );
     this.closeEditModal();
  }
  logout() {
    this.authService.logout();
  }
  public getAuthService(){
    return this.authService;
  }
  private showSuccessMessage(message: string) {
    this.successMessage = message;
    setTimeout(() => {
      this.successMessage = null;
    }, 3000); // Nach 10 Sekunden ausblenden
  }
  private showErrorMessage(message: string) {
    this.errorMessage = message;
    setTimeout(() => {
      this.errorMessage = null;
    }, 3000); // Nach 10 Sekunden ausblenden
  }
}
