import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ParticipanteService } from '../participantes.service';
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

  constructor(
    private participanteService: ParticipanteService,
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
          },
          (error) => {
            console.error('Error al obtener participante:', error);
          }
        );
      }
    });
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
