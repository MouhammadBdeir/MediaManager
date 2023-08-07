import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Benutzerprofile } from './benutzer';
import { environment } from 'src/environments/environment';



@Injectable({providedIn: 'root'})
export class BenutzerService {
  private apiServerUrl=environment.apiBaseUrl;
  constructor(private http: HttpClient){}
  

  public getBenuztern(): Observable<Benutzerprofile[]> {
    return this.http.get<Benutzerprofile[]>(`${this.apiServerUrl}/api/v1/registration/all`, { withCredentials: true });
  }
  public register(benutzer : Benutzerprofile): Observable<Benutzerprofile> {
    return this.http.post<Benutzerprofile>(`${this.apiServerUrl}/api/v1/registration/add`, benutzer, { withCredentials: true });
  }
  public updateBenuzter(benutzer : Benutzerprofile): Observable<Benutzerprofile> {
    return this.http.put<Benutzerprofile>(`${this.apiServerUrl}/api/v1/registration/benuzter/update`, benutzer, { withCredentials: true });
  }
  public deleteBenuzter(benutzerId : number): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/api/v1/registration/delete/${benutzerId}`, { withCredentials: true });
  }
  
  
}