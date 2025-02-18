import { Component } from '@angular/core';
import { ParticipanteService } from '../participantes.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-participantescrear',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './participantescrear.component.html',
  styleUrl: './participantescrear.component.css'
  
})
export class ParticipantescrearComponent {
  participante: any = { nombre: '', apellidos: '', centro: '', especialidadId: null };
  errorMessage: string = '';


  constructor(
    private participanteService: ParticipanteService, 
    private authService: AuthService,
    private router: Router
  ) {}

  crearParticipante(): void {
    if (!this.authService.isExperto()) {
      return;
    }

    this.participanteService.createParticipante(this.participante).subscribe(
      (data) => {
        console.log('Participante creado', data);
        this.router.navigate(['/participantes']);
      },
      (error) => {
        console.error('Error al crear participante', error);
        this.errorMessage = 'Error al crear participante';
      }
    );
  }
}