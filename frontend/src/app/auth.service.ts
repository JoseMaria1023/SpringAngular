import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

interface LoginResponse {
  username: string;
  roles: string[];
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:9000/auth';

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: { username: string; password: string }): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials);
  }

  register(name: string, email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, { name, email, password });
  }

  // Método para obtener los datos del usuario a partir del token
  getUserData() {
    const token = localStorage.getItem('token');
    if (token) {
      return this.decodeJWT(token); // Usamos la función para decodificar el token
    }
    return null;
  }

  // Método para decodificar el token
  decodeJWT(token: string): any {
    const payload = token.split('.')[1];
    const decodedPayload = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
    return JSON.parse(decodedPayload);
  }

  // Método para verificar si el usuario es admin
  isAdmin(): boolean {
    const userData = this.getUserData();
    return userData && userData.roles && userData.roles.includes('ROLE_ADMIN');
  }

  // Método para verificar si el usuario es experto
  isExperto(): boolean {
    const userData = this.getUserData();
    return userData && userData.roles && userData.roles.includes('ROLE_EXPERTO');
  }

  // Método para redirigir al usuario después de login
  redirectUser() {
    if (this.isAdmin()) {
      this.router.navigate(['/especialidades']);  // Redirigir a especialidades para admins
    } else {
      this.router.navigate(['/home']);  // Redirigir al home para otros usuarios
    }
  }
}
