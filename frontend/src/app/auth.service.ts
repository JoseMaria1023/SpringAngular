import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

interface LoginResponse {
  especialidadNombre: any;
  idUser?: number;
  username: string;
  roles?: string[];
  authorities?: string[];
  token: string;
  especialidadId?: number;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:9000/auth';
  private username: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  login(credentials: { username: string; password: string }): Observable<LoginResponse> {
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
  
    if (response.especialidadId) {
      sessionStorage.setItem('especialidadId', response.especialidadId.toString());
    }
  
    if (response.idUser) {
      sessionStorage.setItem('userId', response.idUser.toString());
    }
  
    if (response.especialidadNombre) {  
      sessionStorage.setItem('especialidadNombre', response.especialidadNombre);
    }
  
    this.username = response.username;
  }
  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  getRoles(): string[] {
    try {
      return JSON.parse(sessionStorage.getItem('roles') || '[]');
    } catch {
      return [];
    }
  }
  getUserId(): number | null {  
    const userId = sessionStorage.getItem('userId');
    return userId ? Number(userId) : null;
  }

  getUsername(): string {
    return sessionStorage.getItem('username') || this.username;
  }

  isAdmin(): boolean {
    return this.getRoles().includes('ROLE_ADMIN');
  }

  isExperto(): boolean {
    return this.getRoles().includes('ROLE_EXPERTO');
  }

  getEspecialidadId(): number | null {
    const especialidadId = sessionStorage.getItem('especialidadId');
    return especialidadId ? Number(especialidadId) : null;
  }
  
  getEspecialidadNombre(): string | null {
    return sessionStorage.getItem('especialidadNombre');
  }
  

  logout(): void {
    sessionStorage.clear();
    this.username = '';
    this.router.navigate(['/home']);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  redirectUser(): void {
    if (this.isAdmin()) {
      this.router.navigate(['/admin']);
    } else if (this.isExperto()) {
      this.router.navigate(['/experto']);
    } else {
      this.router.navigate(['/home']);
    }
  }
}
