import { Component, OnInit } from '@angular/core';
import { FilmSerien } from '../media/media';
import { MediaService } from '../media/media.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  medien: FilmSerien[] | undefined;

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