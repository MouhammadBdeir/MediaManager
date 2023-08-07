import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from './login/LoginService '; // Replace 'AuthService' with the actual service you use for authentication.

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.authService.checkAuthStatus() && this.authService.getCurrentUser()?.benuzterrole === 'ADMIN') {
    
      return true;
    } else {
      this.authService.logout();
      return false;
    }
  }
}
