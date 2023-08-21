import { Component, OnInit } from '@angular/core';
import { FilmSerien } from '../media/media';
import { MediaService } from '../media/media.service';
import { AuthService } from '../login/LoginService ';
import { Router } from '@angular/router';
import { BewertungService } from '../bewertung/bewertung.server';
import { Benutzerprofile } from '../user/benutzer';
import { Bewertung } from '../bewertung/bewertung';
import { CommonService } from '../CommonService';
@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {
  medien: FilmSerien[] | undefined;
  newComment: string = '';
  selectedStars: number = 0; 
  hoveredStars: number = 0;
  mediaDraftTextMap: { [mediaId: number]: string } = {};

  constructor(
    private mediaService: MediaService,
    private authService: AuthService,
    private router: Router,
    private bewertungService: BewertungService,
    private commonService: CommonService,

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
    this.commonService.addComment(media, this.newComment, this.selectedStars);
  }

  onStarClick(starNumber: number) {
    this.selectedStars = starNumber;
  }
  hoverStars(starNumber: number) {
    for (let star = 1; star <= starNumber; star++) {
      document.getElementById(`star-${star}`)?.classList.add('hovered');
    }
  }
  unhoverStars() {
    document.querySelectorAll('.star').forEach((star) => {
      star.classList.remove('hovered');
    });
  }
}
