import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ParticipanteService } from '../participantes.service';
import { EspecialidadService } from '../especialidad.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-participanteeditar',
  templateUrl: './participanteeditar.component.html',
  imports: [FormsModule],
  styleUrls: ['./participanteeditar.component.css']
})
export class ParticipanteeditarComponent implements OnInit {
  participante: any = {
    idParticipante: null,
    nombre: '',
    apellidos: '',
    centro: '',
    especialidadId: null,
  };

  especialidadNombre: string = ''; 

  constructor(
    private participanteService: ParticipanteService,
    private especialidadService: EspecialidadService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.participanteService.getParticipanteById(id).subscribe(
          (data) => {
            this.participante = data;
            if (this.participante.especialidadId) {
              this.obtenerNombreEspecialidad(this.participante.especialidadId);
            }
          },
          (error) => {
            console.error('Error al obtener participante:', error);
          }
        );
      }
    });
  }

  obtenerNombreEspecialidad(idEspecialidad: number): void {
    this.especialidadService.getEspecialidades().subscribe(
      (especialidades) => {
        console.log('Especialidades recibidas:', especialidades);
        const especialidad = especialidades.find(esp => esp.idEspecialidad === idEspecialidad);
        this.especialidadNombre = especialidad ? especialidad.nombre : 'Especialidad no encontrada';
      },
      (error) => {
        console.error('Error al obtener especialidades:', error);
      }
    );
  }

  actualizarParticipante(): void {
    this.participanteService.updateParticipante(this.participante.idParticipante, this.participante).subscribe(
      () => {
        this.router.navigate(['participantes/lista']);
      },
      (error) => {
        console.error('Error al actualizar participante:', error);
      }
    );
  }
}
