import { Component, OnInit } from '@angular/core';
import { ParticipanteService } from '../participantes.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-participanteslista',
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './participanteslista.component.html',
  styleUrl: './participanteslista.component.css'
})
export class ParticipanteslistaComponent implements OnInit {
  participantes: any[] = [];
  loading: boolean = true;

  constructor(private participanteService: ParticipanteService) {}

  ngOnInit(): void {
    this.loadParticipantes();
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
}
