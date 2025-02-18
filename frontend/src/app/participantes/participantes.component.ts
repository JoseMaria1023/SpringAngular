import { Component, OnInit } from '@angular/core';
import { ParticipantesService } from '../participantes.service';

@Component({
  selector: 'app-participantes',
  imports: [],
  templateUrl: './participantes.component.html',
  styleUrl: './participantes.component.css'
})
export class ParticipantesComponent implements OnInit {
  participantes: any[] = [];
  especialidadId: number | string = '';

  constructor(private participantesService: ParticipantesService) { }

  ngOnInit(): void {
    this.traerCompetidores();
  }

  traerCompetidores(): void {
    this.participantesService.traerCompetidores(this.especialidadId).subscribe(
      (data) => {
        this.participantes = data;
      },
      (error) => {
        console.error('Error al traer competidores', error);
      }
    );
  }
}