import { Component, OnInit } from '@angular/core';
import { EspecialidadService } from '../especialidad.service';
import { UserService } from '../user.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth.service';
import { catchError, of } from 'rxjs';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-crearexperto',
  imports: [FormsModule,CommonModule],
  templateUrl: './crearexperto.component.html',
  styleUrl: './crearexperto.component.css'
})
export class CrearexpertoComponent implements OnInit {
  experto: any = {
    nombre: '',
    apellidos: '',
    dni: '',
    username: '',
    password: '',
    especialidadId: null,
    role: 'ROLE_EXPERTO'
  };
  especialidades: any[] = [];
  errorMessage: string = '';

  constructor(
    private especialidadService: EspecialidadService,
    private userService: UserService, 
    private router: Router, 
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadEspecialidades();
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

  crearExperto(): void {
    this.authService.createUser(this.experto)
      .subscribe({
        next: (response) => {
          this.router.navigate(['/admin']);
        },
        error: (error) => {
          this.errorMessage = 'Error al crear el experto. Por favor, intenta de nuevo.';
        }
      });
  }
}