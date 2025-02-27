import { Component, OnInit } from '@angular/core';
import { EvaluacionService } from '../evaluacion.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ParticipanteService } from '../participantes.service';
import { EspecialidadService } from '../especialidad.service';

@Component({
  selector: 'app-lista-evaluaciones',
  imports: [CommonModule,FormsModule],
  templateUrl: './lista-evaluaciones.component.html',
  styleUrl: './lista-evaluaciones.component.css'
})
export class ListaEvaluacionesComponent implements OnInit {
  evaluaciones: any[] = [];
  participantes: any[] = []; 
  especialidades: any[] = []; 
  especialidadSeleccionada: string = ''; 

  constructor(
    private evaluacionService: EvaluacionService,
    private participanteService: ParticipanteService,
    private especialidadService: EspecialidadService
  ) {}

  ngOnInit(): void {
    this.obtenerEvaluaciones();
  }

  obtenerEvaluaciones(): void {
    this.evaluacionService.obtenerTodasEvaluaciones().subscribe({
      next: (data) => {
        this.evaluaciones = data.sort((a: any, b: any) => b.notaFinal - a.notaFinal);

        this.participanteService.getParticipantes().subscribe({
          next: (participantes) => {
            this.participantes = participantes;

            this.especialidadService.getEspecialidades().subscribe({
              next: (especialidades) => {
                this.especialidades = especialidades;

                this.evaluaciones.forEach((evaluacion) => {
                  const participante = this.participantes.find(p => p.idParticipante === evaluacion.participanteId);
                  if (participante) {
                    evaluacion.participanteNombre = participante.nombre;
                    const especialidad = this.especialidades.find(e => e.idEspecialidad === participante.especialidadId);
                    evaluacion.especialidad = especialidad ? especialidad.nombre : 'Desconocida';
                  }
                });
              }
            });
          }
        });
      },
    });
  }

  evaluacionesFiltradas(): any[] {
    if (!this.especialidadSeleccionada) return this.evaluaciones;
    return this.evaluaciones.filter(e => e.especialidad === this.especialidadSeleccionada);
  }

  getColor(index: number): string {
    if (index === 0) return 'gold';
    if (index === 1) return 'silver';
    if (index === 2) return 'bronze';
    return '';
  }
}