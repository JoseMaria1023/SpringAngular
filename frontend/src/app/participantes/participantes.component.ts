import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-participantes',
  imports: [],
  templateUrl: './participantes.component.html',
  styleUrl: './participantes.component.css'
})
export class ParticipantesComponent {
  constructor(private router: Router) {}

  verParticipantes(): void {
    this.router.navigate(['/participantes/lista']);
  }

  crearParticipante(): void {
    this.router.navigate(['/experto/participante/crear']);
  }

  editarParticipante(): void {
    this.router.navigate(['/experto/participante/editar']);
  }
}