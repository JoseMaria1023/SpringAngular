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
    const userData = this.authService.getUserData();
    if (userData) {
      this.isLoggedIn = true;
      this.username = userData.username;
    }
  }

  login(username: string, password: string) {
    this.authService.login({ username, password }).subscribe(
      (response) => {
        localStorage.setItem('token', response.token); // Guardamos el token en localStorage
        this.EstadoLogin(); // Actualizamos el estado de login
        this.authService.redirectUser(); // Redirigimos al usuario según el rol
      },
      (error) => {
        console.error('Login error:', error);
      }
    );
  }

  logout() {
    localStorage.removeItem('token'); // Eliminamos el token del localStorage
    this.isLoggedIn = false; // Actualizamos el estado de login
    this.username = ''; 
    this.authService.redirectUser(); // Redirigimos al usuario a la página principal
  }
}
