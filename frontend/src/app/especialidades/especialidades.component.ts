import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-especialidades',
  templateUrl: './especialidades.component.html',
  styleUrls: ['./especialidades.component.css']
})
export class EspecialidadesComponent {

  constructor(private router: Router) {}

  verEspecialidad(): void {
    this.router.navigate(['/admin/especialidad']);
  }

  crearEspecialidad(): void {
    this.router.navigate(['/admin/especialidad/crear']);
  }

  editarEspecialidad(): void {
    this.router.navigate(['/admin/especialidad/editar']);
  }
}




//   especialidades: any[] = [];
//   especialidadEdit: any = null;  
//   newEspecialidad: any = { nombre: '', codigo: '' };
//   isAdmin: boolean = false;
//   isExperto: boolean = false;
//   errorMessage: string = '';

//   constructor(
//     private especialidadService: EspecialidadService,
//     private authService: AuthService
//   ) {}

//   ngOnInit(): void {
//     this.loadEspecialidades();
//     this.isAdmin = this.authService.isAdmin();
//     this.isExperto = this.authService.isExperto();
//   }

//   loadEspecialidades(): void {
//     this.especialidadService.getEspecialidades()
//       .pipe(
//         catchError(err => {
//           this.errorMessage = 'Error al cargar especialidades';
//           return of([]); 
//         })
//       )
//       .subscribe(data => {
//         this.especialidades = data;
//       });
//   }

//   guardarEspecialidad(): void {
//     if (this.especialidadEdit?.idEspecialidad) {
//       this.editarEspecialidad(this.especialidadEdit);
//     } else {
//       this.crearEspecialidad();
//     }
//   }

//   seleccionarEspecialidad(especialidad: any): void {
//     this.especialidadEdit = { ...especialidad };  // Cargar los datos para editar
//   }
  
//   editarEspecialidad(especialidad: any): void {
//     if (!especialidad.idEspecialidad) {
//       this.errorMessage = 'ID de especialidad no válido.';
//       return;
//     }

//     this.especialidadService.updateEspecialidad(especialidad.idEspecialidad, especialidad)
//         .subscribe({
//             next: () => {
//                 this.loadEspecialidades();
//                 this.cancelarEdicion(); 
//             },
//             error: (error) => {
//                 this.errorMessage = 'Error al actualizar la especialidad';
//             }
//         });
//   }

//   crearEspecialidad(): void {
//     this.especialidadService.createEspecialidad(this.newEspecialidad)
//       .subscribe({
//         next: () => {
//           this.loadEspecialidades();
//           this.newEspecialidad = { nombre: '', codigo: '' }; 
//         },
//         error: (error) => {
//           this.errorMessage = 'Error al crear la especialidad';
//         }
//       });
//   }

//   cancelarEdicion(): void {
//     this.especialidadEdit = null; 
//   }
// }
