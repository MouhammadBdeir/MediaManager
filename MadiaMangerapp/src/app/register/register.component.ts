import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Benutzerprofile } from '../user/benutzer';
import { BenutzerService } from '../user/benutzer.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  benutzer: Benutzerprofile = {
    id: 0,
    benutzername: '',
    password: '',
    email: '',
  };
  registrationSuccess: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private benutzerService: BenutzerService,
    private router: Router
  ) {}
  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      benutzername: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });
  }
  register(): void {
    if (this.registerForm.invalid) {
      // Handle invalid form submission, if needed
      return;
    }
    this.benutzer.benutzername = this.registerForm.value.benutzername;
    this.benutzer.password = this.registerForm.value.password;
    this.benutzer.email = this.registerForm.value.email; 
    this.benutzerService.register(this.benutzer).subscribe(
      (response: any) => {
          this.registrationSuccess = true;
          console.log('Registration successful');
          this.router.navigate(['/confirmation']);
          this.clearForm();
      },
      (error) => {
        console.log('Registration failed. Error:', error);
      }
    );
  }
  clearForm(): void {
    this.registerForm.reset();
  }
}
