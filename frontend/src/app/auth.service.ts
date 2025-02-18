import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

interface LoginResponse {
  username: string;
  roles?: string[];     // Puede venir como roles...
  authorities?: string[]; // ...o como authorities
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:9000/auth';
  private username: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: { username: string; password: string }): Observable<LoginResponse> {
    console.log("AuthService: intentando login con", credentials);
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials);
  }

  register(name: string, email: string, password: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/register`, { name, email, password });
  }

  // Guarda token, username y roles en el localStorage
  setSession(response: LoginResponse): void {
    localStorage.setItem('token', response.token);
    localStorage.setItem('username', response.username);
    const roles = response.roles || response.authorities || [];
    localStorage.setItem('roles', JSON.stringify(roles));
    this.username = response.username;
    console.log("AuthService: sesión guardada, username =", response.username);
  }

  getToken(): string | null {
    const token = localStorage.getItem('token');
    console.log("AuthService: getToken() =", token);
    return token;
  }

  // Método para obtener los roles sin decodificar el token
  getRoles(): string[] {
    const rolesStr = localStorage.getItem('roles');
    if (!rolesStr || rolesStr === 'undefined') {
      console.log("AuthService: No roles found");
      return [];
    }
    try {
      const roles = JSON.parse(rolesStr);
      console.log("AuthService: roles =", roles);
      return roles;
    } catch (e) {
      console.error("Error parsing roles from localStorage", e);
      return [];
    }
  }

  getUsername(): string {
    return localStorage.getItem('username') || this.username;
  }

  isAdmin(): boolean {
    const roles = this.getRoles();
    const admin = roles.includes('ROLE_ADMIN');
    console.log("AuthService: isAdmin() =", admin);
    return admin;
  }

  isExperto(): boolean {
    const roles = this.getRoles();
    const experto = roles.includes('ROLE_EXPERTO');
    console.log("AuthService: isExperto() =", experto);
    return experto;
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('roles');
    this.username = '';
    console.log("AuthService: logout, sesión eliminada");
    this.router.navigate(['/home']);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  // Redirige al usuario según su rol
  redirectUser(): void {
    if (this.isAdmin()) {
      console.log("AuthService: redirigiendo admin a /especialidades");
      this.router.navigate(['/especialidades']);
    } else if (this.isExperto()) {
      console.log("AuthService: redirigiendo experto a /home");
      this.router.navigate(['/home']);
    } else {
      console.log("AuthService: redirigiendo usuario a /home");
      this.router.navigate(['/home']);
    }
  }
}
