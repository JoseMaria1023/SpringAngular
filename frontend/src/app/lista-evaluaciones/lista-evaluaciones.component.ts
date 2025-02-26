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
        // Ordenar las evaluaciones de mayor a menor por notaFinal
        this.evaluaciones = data.sort((a: any, b: any) => b.notaFinal - a.notaFinal);
      },
      error: (err) => {
        console.error('Error al obtener evaluaciones', err);
      }
    });
  }

  getColor(index: number): string {
    if (index === 0) return 'gold'; // 🥇 Oro (1er lugar)
    if (index === 1) return 'silver'; // 🥈 Plata (2do lugar)
    if (index === 2) return 'bronze'; // 🥉 Bronce (3er lugar)
    return ''; // Sin color para el resto
  }
}