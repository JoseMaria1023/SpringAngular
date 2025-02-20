import { Component } from '@angular/core';
import { EspecialidadService } from '../especialidad.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-creaespecialidad',
  imports: [CommonModule,FormsModule],
  templateUrl: './creaespecialidad.component.html',
  styleUrl: './creaespecialidad.component.css'
})
export class CreaespecialidadComponent {
  nuevaEspecialidad: any = { nombre: '', codigo: '' };
  errorMessage: string = '';
  exitoMessage: string = '';

  constructor(private especialidadService: EspecialidadService) {}

  crearEspecialidad(): void {
    if (!this.nuevaEspecialidad.nombre || !this.nuevaEspecialidad.codigo) {
      return;
    }

    this.especialidadService.crearEspecialidad(this.nuevaEspecialidad)
      .subscribe({
        next: () => {
          this.nuevaEspecialidad = { nombre: '', codigo: '' };
        },
        error: () => {
          this.errorMessage = 'Error al crear la especialidad';
        }
      });
  }

  cancelarCreacion(): void {
    this.nuevaEspecialidad = { nombre: '', codigo: '' };
  }
}