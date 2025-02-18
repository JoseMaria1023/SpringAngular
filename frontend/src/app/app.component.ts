import { Component, OnInit } from '@angular/core';
import { AuthService } from './auth.service';
import { LogoComponent } from "./logo/logo.component"; // Importa tu servicio de autenticación
import { RouterOutlet } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  imports: [LogoComponent, RouterOutlet, NavbarComponent]
})
export class AppComponent implements OnInit {
  isLoggedIn = false;
  username: string = '';

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    this.EstadoLogin();
  }

  EstadoLogin() {
    if (this.authService.isLoggedIn()) {
      this.isLoggedIn = true;
      this.username = this.authService.getUsername(); 
    }
  }

  login(username: string, password: string) {
    this.authService.login({ username, password }).subscribe(
      (response) => {
        localStorage.setItem('token', response.token);
        this.EstadoLogin(); 
        this.authService.redirectUser(); 
      },
      (error) => {
        console.error('Login error:', error);
      }
    );
  }

  logout() {
    localStorage.removeItem('token'); 
    this.isLoggedIn = false; 
    this.username = ''; 
    this.authService.redirectUser(); 
  }
}