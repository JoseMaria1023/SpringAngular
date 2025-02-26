import { Component, OnInit } from '@angular/core';
import { EvaluacionService } from '../evaluacion.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-lista-evaluaciones',
  imports: [CommonModule,FormsModule],
  templateUrl: './lista-evaluaciones.component.html',
  styleUrl: './lista-evaluaciones.component.css'
})
export class ListaEvaluacionesComponent implements OnInit {
  evaluaciones: any[] = [];

  constructor(private evaluacionService: EvaluacionService) {}

  ngOnInit(): void {
    this.obtenerEvaluaciones();
  }

  obtenerEvaluaciones(): void {
    this.evaluacionService.obtenerTodasEvaluaciones().subscribe({
      next: (data) => {
        this.evaluaciones = data;
      },
      error: (err) => {
        console.error('Error al obtener evaluaciones', err);
      }
    });
  }
}