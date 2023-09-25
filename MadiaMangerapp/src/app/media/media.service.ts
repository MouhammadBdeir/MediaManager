import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FilmSerien } from './media';
import { environment } from 'src/environments/environment';



@Injectable({providedIn: 'root'})
export class MediaService {
  private apiServerUrl=environment.apiBaseUrl;
  constructor(private http: HttpClient){}
  

  public getMedien(): Observable<FilmSerien[]> {
    return this.http.get<FilmSerien[]>(`${this.apiServerUrl}/api/v1/registration/media/all`);
  }
  public add(media : FilmSerien): Observable<FilmSerien> {
    return this.http.post<FilmSerien>(`${this.apiServerUrl}/api/add`, media, { withCredentials: true });
  }
  public update(media : FilmSerien): Observable<FilmSerien> {
    return this.http.put<FilmSerien>(`${this.apiServerUrl}/api/update`, media, { withCredentials: true });
  }
  public addCategori(mediaId : number, cat : string): Observable<FilmSerien> {
    return this.http.post<FilmSerien>(`${this.apiServerUrl}/api/add/categories/8/Top`, { });
  }
  public delete(mediaId : number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/api/delete/${mediaId}`, { withCredentials: true });
  }
  public getMovieById(mediaId : number): Observable<FilmSerien> {
    return this.http.get<FilmSerien>(`${this.apiServerUrl}/api/find/${mediaId}`, { withCredentials: true });
  }
}