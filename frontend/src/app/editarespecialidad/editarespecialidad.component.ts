import { Component, OnInit } from '@angular/core';
import { EspecialidadService } from '../especialidad.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-editarespecialidad',
  templateUrl: './editarespecialidad.component.html',
  styleUrls: ['./editarespecialidad.component.css'],
  imports: [CommonModule, FormsModule]
})
export class EditarespecialidadComponent implements OnInit {
  especialidad: any = null; // Especialidad a editar
  errorMessage: string = '';

  constructor(private especialidadService: EspecialidadService) {}

  ngOnInit() {
    this.especialidad = this.especialidadService.getEspecialidades();
    if (!this.especialidad) {
      this.errorMessage = 'No se encontró la especialidad para editar.';
    }
  }

  guardarEdicion(): void {
    if (!this.especialidad.idEspecialidad) {
      this.errorMessage = 'ID de especialidad no válido.';
      return;
    }

    this.especialidadService.editarEspecialidad(this.especialidad.idEspecialidad)
      .subscribe({
        next: () => {
          console.log('Especialidad actualizada con éxito');
        },
        error: (error) => {
          this.errorMessage = 'Error al actualizar la especialidad';
        }
      });
  }

  cancelarEdicion(): void {
    this.especialidad = null;
  }
}
