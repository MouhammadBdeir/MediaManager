import { Component, OnInit } from '@angular/core';
import { FilmSerien } from '../media/media';
import { MediaService } from '../media/media.service';
import { SafePipe } from '../safe.pipe'
@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  medien: FilmSerien[] | undefined;
  url: string | null = null;
  sanitizer: any;
  constructor(private mediaService: MediaService) { }

  ngOnInit() {
    this.getMedien();
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
 
}
