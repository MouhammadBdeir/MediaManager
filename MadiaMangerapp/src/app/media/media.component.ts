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
    genre: '',
    regisseur: '',
    veroeffentlichungsjahr: 0
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
    genre: '',
    regisseur: '',
    veroeffentlichungsjahr: 0
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
        // Erfolgreich gelöscht, hier kannst du die entsprechende Aktion ausführen, wie z.B. Aktualisieren der Medienliste
      },
      (error) => {
        console.log(error);
      }
    );
  }
  confirmDelete(mediaId: number) {
    if (confirm("Are you sure you want to delete this media?")) {
      // Hier kannst du den Löschvorgang ausführen
      this.deleteMedia(mediaId);
      // Hier kannst du die Seite aktualisieren
      location.reload();
    }
  }
  editMedia(media: FilmSerien) {
    // Implementiere die Logik für die Bearbeitung des Medieneintrags hier
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
    },
    (error) => {
      console.log(error);
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
      // Erfolgreich aktualisiert, hier kannst du die entsprechende Aktion ausführen, wie z.B. Aktualisieren der Medienliste
      this.getMedien();
    },
    (error) => {
      console.log(error);
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
  // Methode zum Bearbeiten eines Benutzers
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
 
}
