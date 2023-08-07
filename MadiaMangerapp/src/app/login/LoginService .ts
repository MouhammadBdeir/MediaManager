import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private isAuthenticated: boolean = false;
  private currentUser: any;
  private apiServerUrl = environment.apiBaseUrl;
  private headers : any;
  private body : any;
  constructor(private http: HttpClient, private router: Router) {
    this.isAuthenticated = localStorage.getItem('authenticated') === 'true';
    this.currentUser = JSON.parse(localStorage.getItem('user_profile') || '{}');
  }

  public setAuthenticated(value: boolean) {
    this.isAuthenticated = value;
    localStorage.setItem('authenticated', value.toString()); 
  }
  public setHeader(headers : any) {
    this.headers = headers;
  }
  public setBody(body : any) {
    this.body = body;
  }
  public getHeader() {
    return  this.headers;
  }
  public getBody() {
    return  this.body;
  }
  public checkAuthStatus() {
    return this.isAuthenticated;
  }

  public setCurrentUser(user: any) {
    this.currentUser = user;
    localStorage.setItem('user_profile', JSON.stringify(user)); 
  }

  public getCurrentUser() {
    return this.currentUser;
  }

  getLoggedInUserName(): string | null {
    return this.isAuthenticated ? (this.currentUser?.name || null) : null;
  }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }
  
  logout(): void {
    this.http.get<void>(`${this.apiServerUrl}/logout`, { withCredentials: true }).subscribe(
      () => {
        this.isAuthenticated = false;
        this.currentUser = null;
        localStorage.removeItem('authenticated');
        localStorage.removeItem('user_profile');
        this.redirectToLogin();
      },
      (error) => {
        console.error('Error during logout:', error);
        this.isAuthenticated = false;
        this.currentUser = null;
        localStorage.removeItem('authenticated');
        localStorage.removeItem('user_profile');
        this.redirectToLogin();
      }
    );
  }
}
