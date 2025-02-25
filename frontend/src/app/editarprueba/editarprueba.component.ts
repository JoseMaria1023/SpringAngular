import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EspecialidadService } from '../especialidad.service';
import { PruebaService } from '../prueba.service';

@Component({
  selector: 'app-editarprueba',
  standalone: true, 
  imports: [CommonModule, FormsModule],
  templateUrl: './editarprueba.component.html',
  styleUrl: './editarprueba.component.css'
})
export class EditarpruebaComponent implements OnInit {
  prueba: any = {};
  especialidades: any[] = [];
  selectedFile: File | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private especialidadService: EspecialidadService,
    private pruebaService: PruebaService
  ) {}

  ngOnInit(): void {
    const pruebaId = this.route.snapshot.paramMap.get('id');
    if (pruebaId) {
      this.cargarPrueba(parseInt(pruebaId, 10));
    }
    this.cargarEspecialidades();
  }

  cargarPrueba(id: number) {
    this.pruebaService.obtenerPruebaPorId(id).subscribe({
      next: (data) => {
        this.prueba = data;
      },
      error: (error) => {
        console.error('Error al cargar la prueba', error);
      }
    });
  }

  cargarEspecialidades() {
    this.especialidadService.getEspecialidades().subscribe({
      next: (data) => {
        this.especialidades = data;
      },
      error: (error) => {
        console.error('Error al cargar las especialidades', error);
      }
    });
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  guardarEdicion(form: NgForm) {
    if (!this.prueba.idPrueba) {
      console.error('Error: idPrueba no está definido');
      return;
    }
  
    const formData = new FormData();
    formData.append('idPrueba', this.prueba.idPrueba.toString());
    formData.append('puntuacionMaxima', this.prueba.puntuacionMaxima.toString());
    formData.append('especialidadId', this.prueba.especialidadId.toString());
    if (this.selectedFile) {
      formData.append('file', this.selectedFile);
    }
  
    this.pruebaService.editarPrueba(formData).subscribe({
      next: () => {
        alert('Prueba actualizada correctamente');
        this.router.navigate(['/pruebas']);
      },
      error: (error) => {
        console.error('Error al actualizar la prueba', error);
      }
    });
  }
}