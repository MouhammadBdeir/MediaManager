import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Bewertung } from './bewertung';
import { environment } from 'src/environments/environment';



@Injectable({providedIn: 'root'})
export class BewertungService {
  private apiServerUrl=environment.apiBaseUrl;
  constructor(private http: HttpClient){}
  

  public getBewertungen(): Observable<Bewertung[]> {
    return this.http.get<Bewertung[]>(`${this.apiServerUrl}/api/bewertung/all`, { withCredentials: true });
  }
  public add(bewertung : Bewertung, id:number): Observable<Bewertung> {
    return this.http.post<Bewertung>(`${this.apiServerUrl}/api/bewertung/add/${id}`, bewertung, { withCredentials: true });
  }
  public update(bewertung : Bewertung): Observable<Bewertung> {
    return this.http.put<Bewertung>(`${this.apiServerUrl}/api/bewertung/update`, bewertung, { withCredentials: true });
  }
  public delete(bewertungId : number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/api/bewertung/delete/${bewertungId}`, { withCredentials: true });
  } 
}