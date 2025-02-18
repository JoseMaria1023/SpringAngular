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

  getUserData() {
    const token = localStorage.getItem('token');
    if (token) {
      return this.decodeJWT(token); 
    }
    return null;
  }

  decodeJWT(token: string): any {
    const payload = token.split('.')[1];
    const decodedPayload = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
    return JSON.parse(decodedPayload);
  }

  isAdmin(): boolean {
    const userData = this.getUserData();
    return userData && userData.roles && userData.roles.includes('ROLE_ADMIN');
  }

  isExperto(): boolean {
    const userData = this.getUserData();
    return userData && userData.roles && userData.roles.includes('ROLE_EXPERTO');
  }

  getUsername(): string {
    const userData = this.getUserData();
    return userData ? userData.username : '';
  }

  logout(): void {
    localStorage.removeItem('token');
    this.router.navigate(['/home']);
  }
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
  redirectUser() {
    if (this.isAdmin()) {
      this.router.navigate(['/especialidades']);  
    } else {
      this.router.navigate(['/home']);  
    }
  }
}
