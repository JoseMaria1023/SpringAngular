import { Component, OnInit } from '@angular/core';
import { EspecialidadService } from '../especialidad.service';
import { CreaespecialidadComponent } from "../creaespecialidad/creaespecialidad.component";
import { EditarespecialidadComponent } from "../editarespecialidad/editarespecialidad.component";
import { CommonModule } from '@angular/common';
import { catchError, of } from 'rxjs';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-listarespecialidad',
  templateUrl: './listarespecialidad.component.html',
  styleUrls: ['./listarespecialidad.component.css'],
  imports: [CreaespecialidadComponent, EditarespecialidadComponent,CommonModule,FormsModule]
})
export class ListarespecialidadComponent implements OnInit {
  especialidades: any[] = [];
  mostrarFormularioCrear: boolean = false;
  especialidadEnEdicion: any = null;

  nuevaEspecialidad: any = { nombre: '', codigo: '' };
  errorMessage: string = '';
  exitoMessage: string = '';

  constructor(private especialidadService: EspecialidadService) {}

  ngOnInit(): void {
    this.cargarEspecialidades();
  }

  cargarEspecialidades(): void {
    this.especialidadService.getEspecialidades()
      .pipe(
        catchError(err => {
          this.errorMessage = 'Error al cargar especialidades';
          return of([]);
        })
      )
      .subscribe(data => {
        this.especialidades = data;
      });
  }

  activarCreacion(): void {
    this.mostrarFormularioCrear = true;
    this.especialidadEnEdicion = null;
  }

  cancelarCreacion(): void {
    this.mostrarFormularioCrear = false;
    this.nuevaEspecialidad = { nombre: '', codigo: '' };
  }

  crearEspecialidad(): void {
    this.especialidadService.crearEspecialidad(this.nuevaEspecialidad)
      .subscribe({
        next: () => {
          this.exitoMessage = 'Especialidad creada con éxito';
          this.cargarEspecialidades();
          this.cancelarCreacion();
        },
        error: () => {
          this.errorMessage = 'Error al crear la especialidad';
        }
      });
  }

  activarEdicion(especialidad: any): void {
    this.especialidadEnEdicion = { ...especialidad };
    this.mostrarFormularioCrear = false;
  }

  cancelarEdicion(): void {
    this.especialidadEnEdicion = null;
  }

  guardarEdicion(): void {
    if (!this.especialidadEnEdicion) return;

    this.especialidadService.editarEspecialidad(this.especialidadEnEdicion)
      .subscribe({
        next: () => {
          this.exitoMessage = 'Especialidad actualizada con éxito';
          this.cargarEspecialidades();
          this.cancelarEdicion();
        },
        error: () => {
          this.errorMessage = 'Error al actualizar la especialidad';
        }
      });
  }
}
