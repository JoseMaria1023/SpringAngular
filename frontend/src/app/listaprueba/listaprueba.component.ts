import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { PruebaService } from '../prueba.service';
import { EvaluacionService } from '../evaluacion.service';
import { ParticipanteService } from '../participantes.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-listaprueba',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './listaprueba.component.html',
  styleUrls: ['./listaprueba.component.css']
})
export class ListapruebaComponent implements OnInit {
  pruebas: any[] = [];
  participantes: any[] = []; 
  selectedPrueba: any = null; 

  constructor(
    private pruebaService: PruebaService,
    private participanteService: ParticipanteService,
    private evaluacionService: EvaluacionService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cargarPruebas();
  }

  cargarPruebas() {
    this.pruebaService.traerTodasLasPruebas().subscribe(
      (data) => {
        this.pruebas = data;
        console.log('Pruebas cargadas:', this.pruebas);
      },
      (error) => {
        console.error('Error cargando pruebas', error);
      }
    );
  }

  selectPrueba(prueba: any) {
    this.selectedPrueba = prueba;
    const especialidadId = this.authService.getEspecialidadId();
    if (!especialidadId) {
      alert('No se pudo obtener la especialidad del experto');
      return;
    }
    this.participanteService.getParticipantes().subscribe(
      (data) => {
        this.participantes = Array.isArray(data) ? data : [data];
        console.log('Participantes cargados:', this.participantes);
      },
      (error) => {
        console.error('Error cargando participantes', error);
      }
    );
  }
  editarPrueba(id: number) {
    this.router.navigate(['/editar-prueba', id]);
  }

  evaluarParticipante(participante: any) {
    const userId = this.authService.getUserId();
    if (!userId) {
      alert('No se pudo obtener el ID del usuario autenticado');
      return;
    }
    if (!this.selectedPrueba) {
      alert('No se ha seleccionado ninguna prueba');
      return;
    }
    this.router.navigate(['/experto/realizar-evaluacion'], {
      queryParams: {
        participanteId: participante.idParticipante,
        pruebaId: this.selectedPrueba.idPrueba
      }
    });
  }
}
