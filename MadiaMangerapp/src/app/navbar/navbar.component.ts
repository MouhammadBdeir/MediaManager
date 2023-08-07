import { Component, OnInit } from '@angular/core';
import { AuthService } from '../login/LoginService ';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  loggedInUserName: any | null = null;
  loggedInRole: any | null = null;
  isLoggedIn: boolean = false;
  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.isLoggedIn = this.authService.checkAuthStatus();
    const currentUser = this.authService.getCurrentUser();
    if (currentUser && currentUser.benutzername) {
      this.loggedInUserName = currentUser.benutzername;
    }
    if (currentUser.benutzerRole === "ADMIN") {
        this.loggedInRole = currentUser.benutzerRole;
    }
  }
  logout() {
    this.authService.logout();
  }
}
