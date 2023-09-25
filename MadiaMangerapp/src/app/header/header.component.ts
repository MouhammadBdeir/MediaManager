import { Component, HostListener } from '@angular/core';
import { FilmSerien } from '../media/media';
import { MediaService } from '../media/media.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  movies!: FilmSerien[];
  constructor(
    private mediaService: MediaService,
    private router: Router,
  ){}
  ngOnInit() {
    this.getMedien();
    
  }
  getMedien() {
    this.mediaService.getMedien().subscribe(
      (data: FilmSerien[]) => {
        this.movies=data;
      },
      (error) => {
        console.log(error);
      }
    );
  }
 
  activeIndex = 0;
  pausedIndex: number | null = null;

  // Adjust the animation duration (in milliseconds)
  animationDuration = 8000; // 8 seconds

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.calculateActiveIndex();
  }

  ngAfterViewInit() {
    this.calculateActiveIndex();
  }

  calculateActiveIndex() {
    const cardWidthPercentage = 0.3; // Adjust the card width percentage
    const screenWidth = window.innerWidth;
    const cardWidth = screenWidth * cardWidthPercentage;

    this.activeIndex = Math.floor((this.activeIndex * cardWidth) / screenWidth);
  }

  pauseSlider(index: number) {
    this.pausedIndex = index;
  }

  resumeSlider() {
    this.pausedIndex = null;
  }
  reload(id:Number){
    this.router.navigateByUrl('/movie/'+id, );
  }
}