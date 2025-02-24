import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';

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
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.post<any>(`${this.apiUrl}/crear-con-pdf`, formData, { headers });
  }
    obtenerEspecialidadDelUsuario(): number | null {
    const token = sessionStorage.getItem('token');
    if (!token) {
      console.error('Token no encontrado');
      return null;
    }
    
    const especialidadId = sessionStorage.getItem('especialidadId');
    if (!especialidadId) {
      console.error('Especialidad no encontrada en sessionStorage');
      return null;
    }

    return parseInt(especialidadId, 10); 
  }
  editarPrueba(prueba: any): Observable<any> {
    const token = sessionStorage.getItem('token');
    
    if (prueba instanceof FormData) {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`
      });
      const idPrueba = prueba.get('idPrueba');
      return this.http.put(`${this.apiUrl}/editar/${idPrueba}`, prueba, { headers });
    } else {
      const headers = new HttpHeaders({
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json'
      });
      return this.http.put(`${this.apiUrl}/editar/${prueba.idPrueba}`, prueba, { headers });
    }
  }
  traerPruebasPorEspecialidad(especialidadId: number): Observable<any[]> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any[]>(`${this.apiUrl}/especialidad/${especialidadId}`, { headers });
  }

  obtenerPruebaPorId(idPrueba: number): Observable<any> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  
    return this.http.get<any>(`${this.apiUrl}/${idPrueba}`, { headers });
  }

  obtenerUltimoIdPrueba(): Observable<number> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({ 'Authorization': `Bearer ${token}` });
    return this.http.get<number>(`${this.apiUrl}/ultimo-id`, { headers });
  }
  traerTodasLasPruebas(): Observable<any[]> {
    const token = sessionStorage.getItem('token');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<any[]>(`${this.apiUrl}/todas`, { headers });
  }
}