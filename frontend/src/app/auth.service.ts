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

  setSession(response: LoginResponse): void {
    sessionStorage.setItem('token', response.token);
    sessionStorage.setItem('username', response.username);
    const roles = response.roles || response.authorities || [];
    sessionStorage.setItem('roles', JSON.stringify(roles));
    this.username = response.username;
    console.log("AuthService: sesión guardada, username =", response.username);
  }

  getToken(): string | null {
    const token = sessionStorage.getItem('token');
    return token;
  }

  getRoles(): string[] {
    const rolesStr = sessionStorage.getItem('roles');
    if (!rolesStr || rolesStr === 'undefined') {
      return [];
    }
    try {
      const roles = JSON.parse(rolesStr);
      return roles;
    } catch (e) {
      return [];
    }
  }

  getUsername(): string {
    return sessionStorage.getItem('username') || this.username;
  }

  isAdmin(): boolean {
    const roles = this.getRoles();
    const admin = roles.includes('ROLE_ADMIN');
    return admin;
  }

  isExperto(): boolean {
    const roles = this.getRoles();
    const experto = roles.includes('ROLE_EXPERTO');
    return experto;
  }

  logout(): void {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('roles');
    this.username = '';
    this.router.navigate(['/home']);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  redirectUser(): void {
    if (this.isAdmin()) {
      this.router.navigate(['/especialidades']);
    } else if (this.isExperto()) {
      this.router.navigate(['/participantes']);
    } else {
      this.router.navigate(['/home']);
    }
  }
}
