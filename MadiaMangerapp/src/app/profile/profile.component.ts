import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../login/LoginService ';
import { Router } from '@angular/router';
import { trigger, state, style, transition, animate } from '@angular/animations';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
  animations: [
    trigger('slideInOut', [
      state('in', style({
        height: '*',
        opacity: 1
      })),
      state('out', style({
        height: '0',
        opacity: 0
      })),
      transition('in <=> out', animate('300ms ease-in-out')),
    ]),
  ],
})
export class ProfileComponent implements OnInit {
  profileData: any;
  isInfoVisible = false;

  constructor(private http: HttpClient, private authService: AuthService,private router: Router) { }

  ngOnInit() {
    if(this.authService.checkAuthStatus()){
      this.fetchProfileData();
    }else{
      this.router.navigateByUrl('/login', ); 
    }
    
  }
  fetchProfileData() {
  this.http.get('http://localhost:8080/profile', { withCredentials: true }).subscribe(
    (response) => {
      this.profileData = response;
      this.authService.setAuthenticated(true);
      this.authService.setCurrentUser(this.profileData); // Store the profile data in local storage
    },
    (error) => {
      console.error('Error fetching profile data:', error);
      
    }
  );
}
toggleInfo() {
    this.isInfoVisible = !this.isInfoVisible;
}
}
