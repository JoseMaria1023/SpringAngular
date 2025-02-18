import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class EspecialidadService {
  private apiUrl = 'http://localhost:9000/api/especialidades';

  constructor(private http: HttpClient) {}

  getEspecialidades() {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  
    return this.http.get<any[]>(`${this.apiUrl}/todos`, { headers }); 
  }

  createEspecialidad(especialidad: any) {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,  
      'Content-Type': 'application/json'
    });

    return this.http.post<any>(`${this.apiUrl}/crear`, especialidad, { headers });
  }

  updateEspecialidad(id: number, especialidad: any): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  
    return this.http.put<any>(`${this.apiUrl}/editar/${id}`, especialidad, { headers }).pipe(
      catchError(error => {
        console.error('Error al actualizar especialidad', error);
        return throwError(() => new Error('Error al actualizar especialidad'));
      })
    );
  }
}