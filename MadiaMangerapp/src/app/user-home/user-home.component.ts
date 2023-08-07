import { Component, OnInit } from '@angular/core';
import { FilmSerien } from '../media/media';
import { MediaService } from '../media/media.service';
import { AuthService } from '../login/LoginService ';
import { Router } from '@angular/router';
import { BewertungService } from '../bewertung/bewertung.server';
import { Benutzerprofile } from '../user/benutzer';
import { Bewertung } from '../bewertung/bewertung';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  medien: FilmSerien[] | undefined;
  newComment: string = '';
  selectedStars: number = 0; 
  constructor(
    private mediaService: MediaService,
    private authService: AuthService,
    private router: Router,
    private bewertungService: BewertungService,
  ) {}

  ngOnInit() {
    if (this.authService.checkAuthStatus()) {
      this.getMedien();
    } else {
      this.router.navigateByUrl('/login');
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

  isCommentEmpty(): boolean {
    return this.newComment.trim().length === 0;
  }

  addComment(media: FilmSerien) {
    const benutzer: Benutzerprofile = {
      id: 0,
      benutzername: "string",
      password: "string",
      email: "string",
    };

    const newBewertung: Bewertung = {
      id: 0,
      benutzerprofil: benutzer,
      filmSerien: media,
      kommentar: this.newComment,
      bewertungswert: this.selectedStars,
    };

    if (this.isCommentEmpty()) {
      return;
    }

    this.bewertungService.add(newBewertung, media.id).subscribe(
      (response: any) => {
        console.log('Comment added successfully!');
        this.newComment = '';
        window.location.reload();
      },
      (error: any) => {
        console.error('Error adding comment:', error);
      }
    );
  }
  onStarClick(starNumber: number) {
    this.selectedStars = starNumber;
  }
}
