import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ParticipantesService {

  private apiUrl = 'http://localhost:9000/api/participantes'; 

  constructor(private http: HttpClient) { }

  traerCompetidores(especialidadId: number | string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/todos?especialidadId=${especialidadId}`);
  }

  crearParticipante(participante: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/crear`, participante);
  }

  editarParticipante(id: number, participante: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/editar/${id}`, participante);
  }
}
