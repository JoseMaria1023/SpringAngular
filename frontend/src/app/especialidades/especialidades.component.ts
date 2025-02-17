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
  especialidadEdit: any = null;
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
    if (this.especialidadEdit?.id) {
      this.editarEspecialidad(this.especialidadEdit);
    } else {
      this.crearEspecialidad();
    }
  }
  
  editarEspecialidad(especialidad: any): void {
    // Asegurarse de que el ID está presente y es válido
    if (!especialidad.id) {
        this.errorMessage = 'ID de especialidad no válido.';
        return;
    }

    this.especialidadService.updateEspecialidad(especialidad.id, especialidad)
        .subscribe({
            next: () => {
                this.loadEspecialidades();
                this.especialidadEdit = null; // Limpiar la edición
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
          this.newEspecialidad = { nombre: '', codigo: '' }; // Limpiar los campos del formulario
        },
        error: (error) => {
          this.errorMessage = 'Error al crear la especialidad';
        }
      });
  }
  
  cancelarEdicion(): void {
    this.especialidadEdit = null; // Limpiar la edición en curso
    this.newEspecialidad = { nombre: '', codigo: '' }; // Limpiar formulario de creación si se está editando
  }
}  