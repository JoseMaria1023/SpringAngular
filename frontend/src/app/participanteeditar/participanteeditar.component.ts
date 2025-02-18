import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { ParticipanteService } from '../participantes.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-participanteeditar',
  templateUrl: './participanteeditar.component.html',
  imports: [CommonModule,FormsModule],
  styleUrls: ['./participanteeditar.component.css']
})
export class ParticipanteeditarComponent implements OnInit {
  
  participanteId!: number;
  participante: any = {};  // Aquí se almacenará el participante a editar.
  loading: boolean = true;

  constructor(
    private route: ActivatedRoute, 
    private participanteService: ParticipanteService, 
    private router: Router
  ) {}

  ngOnInit(): void {
    // Obtener el ID del participante desde la URL
    this.participanteId = +this.route.snapshot.paramMap.get('id')!;
    
    // Cargar los datos del participante
    this.obtenerParticipante();
  }

  // Obtener el participante con el ID desde el backend
  obtenerParticipante() {
    // Suponiendo que en tu backend ya tienes una ruta que te devuelve el participante por ID
    const token = localStorage.getItem('token');
    const headers = {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${token}`,
      })
    };

    this.participanteService.getParticipantes().subscribe(
      (data) => {
        // Buscar el participante por ID
        this.participante = data.find(p => p.id === this.participanteId);
        this.loading = false;
        if (!this.participante) {
          this.router.navigate(['/']);  // Redirigir si no se encuentra el participante
        }
      },
      (error: HttpErrorResponse) => {
        console.error('Error al obtener el participante', error);
        this.router.navigate(['/']);
      }
    );
  }

  // Actualizar el participante
  actualizarParticipante() {
    this.participanteService.updateParticipante(this.participanteId, this.participante).subscribe(
      (data) => {
        console.log('Participante actualizado exitosamente');
        this.router.navigate(['/']); // Redirigir al listado de participantes o a la vista que desees
      },
      (error) => {
        console.error('Error al actualizar el participante', error);
        alert('Error al actualizar el participante');
      }
    );
  }
}
