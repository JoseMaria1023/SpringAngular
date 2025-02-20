import { Component } from '@angular/core';
import { EspecialidadService } from '../especialidad.service';
import { CreaespecialidadComponent } from "../creaespecialidad/creaespecialidad.component";
import { EditarespecialidadComponent } from "../editarespecialidad/editarespecialidad.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-listarespecialidad',
  templateUrl: './listarespecialidad.component.html',
  styleUrls: ['./listarespecialidad.component.css'],
  imports: [CreaespecialidadComponent, EditarespecialidadComponent,CommonModule]
})
export class ListarespecialidadComponent {
  especialidades: any[] = [];  // Lista de especialidades
  especialidadEdit: any = null; // Especialidad que se va a editar
  mostrarFormularioCrear: boolean = false;
  errorMessage: string = '';

  constructor(public especialidadService: EspecialidadService) {}

  ngOnInit() {
    this.loadEspecialidades();
  }

  // Método para cargar las especialidades
  loadEspecialidades(): void {
    this.especialidadService.getEspecialidades()
      .subscribe({
        next: (data) => {
          this.especialidades = data;
        },
        error: (error) => {
          this.errorMessage = 'Error al cargar las especialidades';
        }
      });
  }

  activarEdicion(especialidad: any): void {
    this.especialidadService.setEspecialidad(especialidad);
  }

  activarCreacion(): void {
    this.mostrarFormularioCrear = true;
  }
}
