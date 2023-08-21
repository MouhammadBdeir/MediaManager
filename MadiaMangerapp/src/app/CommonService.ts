import { Injectable } from '@angular/core';
import { BenutzerService } from '../app/user/benutzer.service';
import { MediaService } from '../app/media/media.service';
import { FilmSerien } from '../app/media/media';
import { Benutzerprofile } from '../app/user/benutzer';
import { BewertungService } from './bewertung/bewertung.server';
import { Bewertung } from './bewertung/bewertung';

@Injectable({ providedIn: 'root' })
export class CommonService {
  constructor(
    private benutzerService: BenutzerService,
    private mediaService: MediaService,
    private bewertungService: BewertungService
  ) {}
  newComment: string = '';

  addComment(media: FilmSerien, newComment: string, selectedStars: number) {
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
      kommentar: newComment,
      bewertungswert: selectedStars,
    };
  
    if (this.isCommentEmpty(newComment)) {
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
  
  isCommentEmpty(newComment:string ): boolean {
    return newComment.trim().length === 0;
  }
  getUserMediaCount() {
  }
}
