import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from './auth.service'; 


@Injectable({
  providedIn: 'root',
})
export class ParticipanteService {
  private apiUrl = 'http://localhost:9000/api/participantes';

  constructor(private http: HttpClient,private authService: AuthService) {}

  getParticipantes(): Observable<any[]> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });

    return this.http.get<any[]>(`${this.apiUrl}/todos`, { headers });
  }

  createParticipante(participante: any): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    participante.especialidadId = this.authService.getEspecialidadId();

    return this.http.post<any>(`${this.apiUrl}/crear`, participante, { headers });
  }
  getParticipanteById(id: number): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });
  
    return this.http.get<any>(`${this.apiUrl}/buscar/${id}`, { headers }).pipe(
      catchError(error => {
        return throwError(() => new Error('Error al obtener participante'));
      })
    );
  }
  

  updateParticipante(id: number, participante: any): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.put<any>(`${this.apiUrl}/editar/${id}`, participante, { headers }).pipe(
      catchError(error => {
        return throwError(() => new Error('Error al actualizar participante'));
      })
    );
  }
}
