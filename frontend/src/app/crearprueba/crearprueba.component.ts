import { Component, OnInit } from '@angular/core';
import { EspecialidadService } from '../especialidad.service';
import { PruebaService } from '../prueba.service';
import { ItemService } from '../item.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CrearitemComponent } from "../crearitem/crearitem.component";

@Component({
  selector: 'app-crearprueba',
  imports: [CommonModule, FormsModule, CrearitemComponent],
  templateUrl: './crearprueba.component.html',
  styleUrl: './crearprueba.component.css'
})
export class CrearpruebaComponent implements OnInit {
  file: File | null = null;
  puntuacionMaxima: number | null = null;
  especialidadId: number | null = null;
  pruebaCreada: any = null; 

  especialidades: any[] = [];

  constructor(
    private especialidadService: EspecialidadService,
    private pruebaService: PruebaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.especialidadService.getEspecialidades().subscribe(
      data => {
        this.especialidades = data;
      },
      error => {
        console.error('Error al cargar especialidades', error);
      }
    );
    const espIdStr = sessionStorage.getItem('especialidadId');
    if (espIdStr) {
      this.especialidadId = Number(espIdStr);
    } else {
      console.error('Especialidad no encontrada en sessionStorage');
    }
  }

  onFileChange(event: any): void {
    const selected = event.target.files[0];
    if (selected) {
      this.file = selected;
    }
  }

  crearPrueba(form: NgForm): void {
    if (!this.file || !this.puntuacionMaxima || !this.especialidadId) {
      alert('Por favor, completa todos los campos para crear la prueba.');
      return;
    }
  
    this.pruebaService.crearPruebaConPDF(this.file, this.puntuacionMaxima, this.especialidadId)
      .subscribe(
        response => {
          alert('Prueba creada exitosamente.');
          this.pruebaCreada = response;
          if (this.pruebaCreada.idPrueba) {
            console.log('Prueba creada con id:', this.pruebaCreada.idPrueba);
          } else {
            console.error('El idPrueba recibido es null:', response);
          }
        },
        error => {
          console.error('Error creando la prueba', error);
          alert('Error al crear la prueba.');
        }
      );
  }
}  