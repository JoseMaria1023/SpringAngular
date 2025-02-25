import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EvaluacionItemService {
  private apiUrl = 'http://localhost:9000/api/evaluacion-items'; 

  constructor(private http: HttpClient) {}

  obtenerEnunciadoDePrueba(idPrueba: number): Observable<string> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    const url = `${this.apiUrl}/enunciado/prueba/${idPrueba}`;
    return this.http.get<string>(url, { headers });
  }

  obtenerItemsDePrueba(idPrueba: number): Observable<any[]> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    const url = `${this.apiUrl}/items/prueba/${idPrueba}`;
    return this.http.get<any[]>(url, { headers });
  }

  enviarEvaluacion(evaluaciones: any[]): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.post<any>(`${this.apiUrl}/evaluar`, evaluaciones, { headers });
  }
  actualizarNotaFinal(idEvaluacion: number): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this.http.put<any>(`${this.apiUrl}/actualizar-nota/${idEvaluacion}`, {}, { headers });
  }
obtenerIdEvaluacionPorPrueba(idPrueba: number): Observable<number | null> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const url = `${this.apiUrl}/prueba/${idPrueba}`;
    return this.http.get<number>(url, { headers });
  }
}