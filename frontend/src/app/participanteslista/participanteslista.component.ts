import { Component, OnInit } from '@angular/core';
import { ParticipanteService } from '../participantes.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { EspecialidadService } from '../especialidad.service';

@Component({
  selector: 'app-participanteslista',
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './participanteslista.component.html',
  styleUrl: './participanteslista.component.css'
})
export class ParticipanteslistaComponent implements OnInit {
  participantes: any[] = [];
  especialidades: any[] = [];  // Arreglo para almacenar las especialidades
  loading: boolean = true;

  constructor(
    private participanteService: ParticipanteService,
    private especialidadService: EspecialidadService  // Inyectar el servicio de especialidades
  ) {}

  ngOnInit(): void {
    this.loadParticipantes();
    this.loadEspecialidades();  // Cargar las especialidades
  }

  loadParticipantes(): void {
    this.participanteService.getParticipantes().subscribe(
      (data) => {
        this.participantes = data;
        this.loading = false;
      },
      (error) => {
        console.error('Error al cargar los participantes', error);
        this.loading = false;
      }
    );
  }

  loadEspecialidades(): void {
    this.especialidadService.getEspecialidades().subscribe(
      (data) => {
        this.especialidades = data; // Guardar las especialidades obtenidas
      },
      (error) => {
        console.error('Error al cargar las especialidades', error);
      }
    );
  }

  // Método para obtener el nombre de la especialidad por ID
  getEspecialidadNombre(especialidadId: number): string {
    const especialidad = this.especialidades.find((e) => e.idEspecialidad === especialidadId);
    return especialidad ? especialidad.nombre : 'Especialidad no encontrada';
  }
}