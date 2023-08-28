import { Component, Input, OnInit, OnChanges, EventEmitter, Output } from '@angular/core';
import { Bewertung } from './bewertung';
import { BewertungService } from './bewertung.server';
import { NavigationEnd, Router } from '@angular/router';
import { Benutzerprofile } from '../user/benutzer';
import { AuthService } from '../login/LoginService ';
import { MediaComponent } from '../media/media.component';
import { FilmSerien } from '../media/media';
import { CommonService } from '../CommonService';
import { Observable, filter, map } from 'rxjs';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-bewertung',
  templateUrl: './bewertung.component.html',
  styleUrls: ['./bewertung.component.css']
})

export class BewertungComponent implements OnInit, OnChanges {
  
  @Input() inputMedia!: FilmSerien ;
  comments!: Bewertung[] ;
  isDropdownOpen = false;
  loggedInUserName: any | null = null;
  loggedInEmail: any | null = null;
  loggedInRole: any | null = null;
  isHovered: boolean = false;
  hideDiv: boolean = false;

  newComment: string = '';
  selectedStars: number = 0; 
  hoveredStars: number = 0;
  mediaDraftTextMap: { [mediaId: number]: string } = {};


  constructor(
    private bewertungService: BewertungService,
    private authService: AuthService,
    private router: Router,
    private commonService: CommonService,
    private activatedRoute: ActivatedRoute

  ) {}
  ngOnInit() {
    document.addEventListener('click', () => this.closeDropdown());
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

  shouldShowDiv(): boolean {
    return this.activatedRoute.snapshot.url[0]?.path !== 'media';
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
  toggleDropdown(event: MouseEvent): void {
    event.stopPropagation();
    this.isDropdownOpen = !this.isDropdownOpen;
  }
  closeDropdown(): void {
    this.isDropdownOpen = false;
  }
  isCommentEmpty(): boolean {
    
    return this.newComment.trim().length === 0 || this.selectedStars==0;
  }
  addComment(media: FilmSerien) {
    this.commonService.addComment(media, this.newComment, this.selectedStars);
  }
  onStarClick(starNumber: number) {
    this.selectedStars = starNumber;
  }
  hoverStars(starNumber: number) {
    if (this.inputMedia) {
      const mediaId = this.inputMedia.id;
      for (let star = 1; star <= starNumber && star <= this.selectedStars; star++) {
        document.getElementById(`media-${mediaId}-star-${star}`)?.classList.add('hovered');
      }
      
    }
  }  
  unhoverStars() {
    if (this.inputMedia) {
      const mediaId = this.inputMedia.id;
      document.querySelectorAll(`.media-${mediaId}-star`).forEach((star) => {
        star.classList.remove('hovered');
      });
    }
  }
  

}
