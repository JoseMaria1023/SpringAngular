import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router, RouterModule } from '@angular/router';
import { EspecialidadService } from '../especialidad.service';
import { CommonModule, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  imports: [CommonModule,FormsModule,NgFor,RouterModule],
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  especialidadNombre: string = '';

  constructor(
    public authService: AuthService,
    private router: Router,
    private especialidadService: EspecialidadService
  ) {}

  ngOnInit(): void {
    if (this.authService.isExperto()) {
      this.obtenerEspecialidad();
    }
  }

  obtenerEspecialidad(): void {
    this.especialidadService.getEspecialidades().subscribe({
      next: (especialidades) => {
        const especialidadId = this.authService.getEspecialidadId();
        const especialidad = especialidades.find(e => e.idEspecialidad === especialidadId);
        this.especialidadNombre = especialidad ? especialidad.nombre : 'Especialidad';
      },
      error: () => {
        this.especialidadNombre = 'Especialidad';
      }
    });
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
