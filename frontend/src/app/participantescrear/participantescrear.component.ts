import { Component, OnInit } from '@angular/core';
import { ParticipanteService } from '../participantes.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-participantescrear',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './participantescrear.component.html',
  styleUrls: ['./participantescrear.component.css']
})
export class ParticipantescrearComponent implements OnInit {
  participante = {
    nombre: '',
    apellidos: '',
    centro: '',
    especialidadId: 0
  };
  especialidadInput: string = ''; 

  constructor(
    private participanteService: ParticipanteService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const especialidadId = this.authService.getEspecialidadId();
    const especialidadNombre = this.authService.getEspecialidadNombre();

    if (especialidadId && especialidadNombre) {
      this.participante.especialidadId = especialidadId;
      this.especialidadInput = `${especialidadId} - ${especialidadNombre}`; // Concatenamos el ID y nombre
    } else {
      alert('No tienes una especialidad asignada.');
      this.router.navigate(['/home']);
    }
  }

  crearParticipante(): void {
    this.participanteService.createParticipante(this.participante).subscribe({
      next: () => {
        this.router.navigate(['/participantes']);
      }
    });
  }
}
