import { Component, OnInit } from '@angular/core';
import { EspecialidadService } from '../especialidad.service';
import { AuthService } from '../auth.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';

@Component({
  selector: 'app-especialidades',
  templateUrl: './especialidades.component.html',
  imports: [CommonModule, FormsModule],
  styleUrls: ['./especialidades.component.css']
})
export class EspecialidadesComponent implements OnInit {
  especialidades: any[] = [];
  especialidadEdit: any = null;  // Inicialmente null para mostrar el formulario de crear
  newEspecialidad: any = { nombre: '', codigo: '' };
  isAdmin: boolean = false;
  isExperto: boolean = false;
  errorMessage: string = '';

  constructor(
    private especialidadService: EspecialidadService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadEspecialidades();
    this.isAdmin = this.authService.isAdmin();
    this.isExperto = this.authService.isExperto();
  }

  loadEspecialidades(): void {
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

  guardarEspecialidad(): void {
    if (this.especialidadEdit?.idEspecialidad) {
      this.editarEspecialidad(this.especialidadEdit);
    } else {
      this.crearEspecialidad();
    }
  }

  seleccionarEspecialidad(especialidad: any): void {
    this.especialidadEdit = { ...especialidad };  // Cargar los datos para editar
  }
  
  editarEspecialidad(especialidad: any): void {
    if (!especialidad.idEspecialidad) {
      this.errorMessage = 'ID de especialidad no válido.';
      return;
    }

    this.especialidadService.updateEspecialidad(especialidad.idEspecialidad, especialidad)
        .subscribe({
            next: () => {
                this.loadEspecialidades();
                this.cancelarEdicion(); // Limpiar la edición después de actualizar
            },
            error: (error) => {
                this.errorMessage = 'Error al actualizar la especialidad';
            }
        });
  }

  crearEspecialidad(): void {
    this.especialidadService.createEspecialidad(this.newEspecialidad)
      .subscribe({
        next: () => {
          this.loadEspecialidades();
          this.newEspecialidad = { nombre: '', codigo: '' }; // Limpiar el formulario de creación
        },
        error: (error) => {
          this.errorMessage = 'Error al crear la especialidad';
        }
      });
  }

  cancelarEdicion(): void {
    this.especialidadEdit = null; // Volver al formulario de crear
  }
}
