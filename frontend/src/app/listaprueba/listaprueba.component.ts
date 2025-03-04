import { Component, OnInit } from '@angular/core';
import { CommonModule, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { PruebaService } from '../prueba.service';
import { EvaluacionService } from '../evaluacion.service';
import { ParticipanteService } from '../participantes.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-listaprueba',
  standalone: true,
  imports: [CommonModule, FormsModule, NgFor, RouterModule],
  templateUrl: './listaprueba.component.html',
  styleUrls: ['./listaprueba.component.css']
})
export class ListapruebaComponent implements OnInit {
  pruebas: any[] = [];
  pruebasPaginadas: any[] = []; 
  participantes: any[] = []; 
  selectedPrueba: any = null;
  paginaActual: number = 1;
  pruebasPorPagina: number = 5; 
  especialidadId: number | null = null;  // ID de la especialidad del usuario logueado

  constructor(
    private pruebaService: PruebaService,
    private participanteService: ParticipanteService,
    private evaluacionService: EvaluacionService,
    private authService: AuthService,
    public router: Router
  ) {
    this.especialidadId = Number(this.authService.getEspecialidadId()); // Obtener la especialidad del usuario
  }

  ngOnInit() {
    this.cargarPruebas();
  }

  cargarPruebas() {
    this.pruebaService.traerTodasLasPruebas().subscribe(
      (data) => {
        // Filtrar pruebas por la especialidad del usuario logueado
        this.pruebas = data.filter(prueba => prueba.especialidadId === this.especialidadId);
        this.actualizarPaginacion();
      },
      (error) => {
        console.error('Error cargando pruebas', error);
      }
    );
  }

  actualizarPaginacion() {
    const inicio = (this.paginaActual - 1) * this.pruebasPorPagina;
    const fin = inicio + this.pruebasPorPagina;
    this.pruebasPaginadas = this.pruebas.slice(inicio, fin);
  }

  cambiarPagina(direccion: number) {
    const totalPaginas = Math.ceil(this.pruebas.length / this.pruebasPorPagina);
    if (this.paginaActual + direccion > 0 && this.paginaActual + direccion <= totalPaginas) {
      this.paginaActual += direccion;
      this.actualizarPaginacion();
    }
  }

  selectPrueba(prueba: any) {
    this.selectedPrueba = prueba;
    this.participanteService.getParticipantes().subscribe(
      (data) => {
        this.participantes = (Array.isArray(data) ? data : [data])
          .filter(participante => participante.especialidadId === prueba.especialidadId);
      },
      (error) => {
        console.error('Error cargando participantes', error);
      }
    );
  }

  navegarCrearPrueba() {
    this.router.navigate(['/experto/crear-prueba']);
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