import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ParticipanteService } from '../participantes.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-home',
  standalone: true, 
  templateUrl: './home.component.html',
  imports: [CommonModule],
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  participantes: any[] = [];
  mostrarParticipantes = false;

  constructor(private router: Router,private participanteService: ParticipanteService) {}

  goToLogin() {
    this.router.navigate(['/login']);
  }
  
  cargarParticipantes(): void {
    this.participanteService.getParticipantes().subscribe(
      (data) => {
        this.participantes = data;
        this.mostrarParticipantes = true; 
      },
      (error) => {
        console.error('Error al obtener los participantes', error);
        this.mostrarParticipantes = false; 
      }
    );
  }
}
