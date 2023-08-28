import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { MediaService } from '../media/media.service';
import { FilmSerien } from '../media/media';

@Component({
  selector: 'app-movie-details',
  templateUrl: './movie-details.component.html',
  styleUrls: ['./movie-details.component.css']
})
export class MovieDetailsComponent implements OnInit {
  movie: FilmSerien | undefined; 

  constructor(
     private route: ActivatedRoute,
     private mediaService: MediaService
    ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id'); 
    const movieId = parseInt(id!, 10); 

    this.mediaService.getMovieById(movieId).subscribe(
      (movie: FilmSerien) => {
        this.movie = movie; 
        console.log(movie)
      },
      (error) => {
        console.error('Error fetching movie:', error);
      }
    );
  }
}
