import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpErrorResponse, HttpHeaders, HttpParams } from '@angular/common/http';
import { AuthService } from './LoginService ';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  errorMessage: string | null = null;
  username: string = "";
  password: string = "";

  constructor(private router: Router, private http: HttpClient, private authService: AuthService) { }

  login() {
    let headers = new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded');
    let body = new HttpParams()
      .set('username', this.username)
      .set('password', this.password)
      .toString();

    // Füge Basic-Authentifizierung hinzu, wenn Benutzername und Passwort vorhanden sind
    if (this.username && this.password) {
      const authToken = btoa(this.username + ':' + this.password);
      headers = headers.set('Authorization', 'Basic ' + authToken);
    }

    this.http.post('http://localhost:8080/login', body, { headers, withCredentials: true }).subscribe(
      (response) => {

        this.authService.setHeader(headers);
        this.authService.setBody(body);
        this.authService.setCurrentUser(response);
        this.authService.setAuthenticated(true); 
        this.router.navigateByUrl('/profile', ); 
      },
      (error: HttpErrorResponse) => {
        console.error('Login error', error);
        console.log(error.error); 
        if (error.status === 401) {
          // Unauthorized error
          this.showErrorMessage("Wrong email or password please try again")
        } else if (error.status === 400) {
          // Bad request error
          this.showErrorMessage("Bad request error")
        } else {
          // Other error scenarios
          this.showErrorMessage("error")
        }
      }
    );
  }
  private showErrorMessage(message: string) {
    this.errorMessage = message;
    setTimeout(() => {
      this.errorMessage = null;
    }, 3000); // Nach 10 Sekunden ausblenden
  }
}
