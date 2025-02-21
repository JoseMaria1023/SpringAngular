import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PruebaService {
  private apiUrl = 'http://localhost:9000/api/pruebas';

  constructor(private http: HttpClient) { }

  crearPruebaConPDF(file: File, puntuacionMaxima: number, especialidadId: number): Observable<any> {
    const formData = new FormData();
    formData.append('file', file, file.name);
    formData.append('puntuacionMaxima', puntuacionMaxima.toString());
    formData.append('especialidadId', especialidadId.toString());

    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    
    return this.http.post(`${this.apiUrl}/crear-con-pdf`, formData, { headers });
  }
  getPruebasByEspecialidad(especialidadId: number): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.apiUrl}/especialidad/${especialidadId}`, { headers });
  }
}