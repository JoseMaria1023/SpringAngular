import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class ParticipanteService {
  private apiUrl = 'http://localhost:9000/api/participantes';

  constructor(private http: HttpClient) {}

  // Obtener todos los participantes
  getParticipantes(): Observable<any[]> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });

    return this.http.get<any[]>(`${this.apiUrl}/todos`, { headers });
  }

  createParticipante(participante: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.post<any>(`${this.apiUrl}/crear`, participante, { headers });
  }

  // Actualizar un participante existente
  updateParticipante(id: number, participante: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json',
    });

    return this.http.put<any>(`${this.apiUrl}/editar/${id}`, participante, { headers }).pipe(
      catchError(error => {
        console.error('Error al actualizar participante', error);
        return throwError(() => new Error('Error al actualizar participante'));
      })
    );
  }
}
