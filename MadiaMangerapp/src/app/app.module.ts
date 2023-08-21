import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './index/index.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { HttpClientModule } from '@angular/common/http';
import { ConfirmationComponent } from './confirmation/confirmation.component';
import { MediaComponent } from './media/media.component';
import { BewertungComponent } from './bewertung/bewertung.component';
import { ProfileComponent } from './profile/profile.component';
import { AuthService } from './login/LoginService ';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NavbarComponent } from './navbar/navbar.component';
import { UserHomeComponent } from './user-home/user-home.component';
import { FooterComponent } from './footer/footer.component'; 
import { SafePipe } from './safe.pipe';

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    LoginComponent,
    RegisterComponent,
    ConfirmationComponent,
    MediaComponent,
    BewertungComponent,
    ProfileComponent,
    NavbarComponent,
    UserHomeComponent,
    FooterComponent,
    SafePipe ,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule, 
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
