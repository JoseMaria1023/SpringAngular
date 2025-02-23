import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EvaluacionService  {
  private apiUrl = 'http://localhost:9000/api/evaluaciones';

  constructor(private http: HttpClient) {}

  evaluarPrueba(pruebaId: number): Observable<any[]> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any[]>(`${this.apiUrl}/prueba/${pruebaId}`, { headers });
  }

  evaluarParticipante(evaluacion: any): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post(`${this.apiUrl}/evaluar`, evaluacion, { headers });
  }
}