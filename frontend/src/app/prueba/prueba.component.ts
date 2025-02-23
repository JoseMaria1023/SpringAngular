import { Component, OnInit } from '@angular/core';
import { FormControl, FormsModule, NgForm, Validators } from '@angular/forms';
import { PruebaService } from '../prueba.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { EspecialidadService } from '../especialidad.service';

@Component({
  selector: 'app-prueba',
  imports: [CommonModule,FormsModule],
  templateUrl: './prueba.component.html',
  styleUrl: './prueba.component.css'
})
export class PruebaComponent implements OnInit {
  especialidades: any[] = [];
  file: File | null = null;
  puntuacionMaxima: number | null = null;
  especialidadId: number | null = null;

  constructor(
    private especialidadService: EspecialidadService,
    private pruebaService: PruebaService
  ) {}

  ngOnInit(): void {
    this.cargarEspecialidades();
  }

  cargarEspecialidades(): void {
    this.especialidadService.getEspecialidades().subscribe(
      (data: any[]) => {
        this.especialidades = data;
      },
      error => {
        console.error('Error al cargar especialidades', error);
      }
    );
  }

  onFileChange(event: any): void {
    const selectedFile = event.target.files[0];
    if (selectedFile) {
      this.file = selectedFile;
    }
  }

  onSubmit(): void {
    if (!this.file || !this.puntuacionMaxima || !this.especialidadId) {
      return;
    }

    this.pruebaService.crearPruebaConPDF(this.file, this.puntuacionMaxima, this.especialidadId)
      .subscribe(
        response => {
          console.log('Prueba creada exitosamente', response);
          alert('Prueba creada exitosamente');
          this.file = null;
          this.puntuacionMaxima = null;
          this.especialidadId = null;
        },
        error => {
          console.error('Error creando la prueba', error);
        }
      );
  }
}