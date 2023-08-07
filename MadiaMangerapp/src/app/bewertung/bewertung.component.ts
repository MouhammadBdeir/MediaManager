import { Component, Input, OnInit, OnChanges, EventEmitter, Output } from '@angular/core';
import { Bewertung } from './bewertung';
import { BewertungService } from './bewertung.server';
import { Router } from '@angular/router';
import { Benutzerprofile } from '../user/benutzer';
import { AuthService } from '../login/LoginService ';
import { MediaComponent } from '../media/media.component';
import { FilmSerien } from '../media/media';
@Component({
  selector: 'app-bewertung',
  templateUrl: './bewertung.component.html',
  styleUrls: ['./bewertung.component.css']
})

export class BewertungComponent implements OnInit, OnChanges {
  @Input() inputMedia!: FilmSerien ;
  comments!: Bewertung[] ;
  loggedInUserName: any | null = null;
  loggedInEmail: any | null = null;
  loggedInRole: any | null = null;

  constructor(
    private bewertungService: BewertungService,
    private authService: AuthService,
    private router: Router
  ) {}
  ngOnInit() {
    if (this.authService.checkAuthStatus()) {
      this.getComments();
    } else {
      this.router.navigateByUrl('/login');
    }
    const currentUser = this.authService.getCurrentUser();
    if (currentUser && currentUser.benutzername && currentUser.email) {
      this.loggedInUserName = currentUser.benutzername;
      this.loggedInEmail=currentUser.email;
    }
    if (currentUser.benutzerRole === "ADMIN") {
        this.loggedInRole = currentUser.benutzerRole;
    }
  }
  ngOnChanges() {
    this.getComments();
  }
  getComments() {
    this.bewertungService.getBewertungen().subscribe(
      (data: Bewertung[]) => {
        this.findCommentsForMedia(data);
        this.comments = this.findCommentsForMedia(data);
      },
      (error) => {
        console.log('Error fetching comments:', error);
      }
    );
  }
  findCommentsForMedia(data: Bewertung[]): Bewertung[] {
    const commentsForMedia: Bewertung[] = [];
    if (this.inputMedia?.id) {
      for (const bewertung of data) {
        if (bewertung.filmSerien?.id === this.inputMedia.id) {
          commentsForMedia.push(bewertung);
        }
      }
    } else {
      console.log('inputMedia is undefined. Unable to filter comments.');
    }
    return commentsForMedia;
  }
  checkMediaMatch(bewertung: Bewertung): boolean {
    
    if (!bewertung.filmSerien || !this.inputMedia) {
      return false;
    }
    return bewertung.filmSerien.id === this.inputMedia.id;
  }
  deleteComment(commentId:number){
    this.bewertungService.delete(commentId).subscribe(
      () => {
        console.log('Bewertung mit dem ID '+commentId+' wurde erfolgreich gelöscht ');
        window.location.reload();
      },
      (error) => {
        console.log(error);
      }
    );
  }
  mediaRatingCount(data: Bewertung[]): number {
    const mediaData = this.findCommentsForMedia(data);
    let count = 0;
    for (const bewertung of mediaData) {
      count += bewertung.bewertungswert;
    }
    return count;
  }
  
}
