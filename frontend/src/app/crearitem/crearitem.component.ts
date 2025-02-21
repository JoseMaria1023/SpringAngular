import { Component, OnInit } from '@angular/core';
import { ItemService } from '../item.service';
import { EspecialidadService } from '../especialidad.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { PruebaService } from '../prueba.service';

@Component({
  selector: 'app-crearitem',
  imports: [CommonModule, FormsModule],
  templateUrl: './crearitem.component.html',
  styleUrl: './crearitem.component.css'
})
export class CrearitemComponent implements OnInit {
  item: any = {
    descripcion: '',
    peso: null,
    gradosConsecucion: null,
    pruebaId: null
  };

  especialidades: any[] = [];  // Lista de especialidades
  pruebas: any[] = [];         // Lista de pruebas asociadas con la especialidad seleccionada

  // Variable para manejar la especialidad seleccionada
  selectedEspecialidadId: number | null = null;

  constructor(
    private itemService: ItemService,
    private especialidadService: EspecialidadService,
    private pruebaService: PruebaService
  ) {}

  ngOnInit() {
    // Cargar especialidades al iniciar el componente
    this.especialidadService.getEspecialidades().subscribe(
      (data) => {
        this.especialidades = data;
      },
      (error) => {
        console.error('Error cargando especialidades', error);
      }
    );
  }

  // Método que se dispara al cambiar la especialidad
  onEspecialidadChange() {
    console.log('Especialidad seleccionada:', this.selectedEspecialidadId);
    if (this.selectedEspecialidadId != null) {
      this.pruebaService.getPruebasByEspecialidad(this.selectedEspecialidadId).subscribe(
        (data) => {
          this.pruebas = data;
        },
        (error) => {
          console.error('Error cargando pruebas', error);
        }
      );
    } else {
      console.error('No se ha seleccionado una especialidad válida');
    }
  }

  // Método para manejar el cambio de prueba seleccionada
  onPruebaChange() {
    console.log('Prueba seleccionada:', this.item.pruebaId);
  }

  // Método para enviar el formulario
  onSubmit() {
    if (!this.item.pruebaId) {
      console.error('Se debe seleccionar una prueba');
      return;
    }
    // Asignar opcionalmente la especialidad seleccionada al objeto item
    this.item.especialidadId = this.selectedEspecialidadId;

    this.itemService.crearItem(this.item).subscribe(
      (response) => {
        console.log('Item creado exitosamente', response);
        // Aquí puedes limpiar el formulario o redirigir
      },
      (error: HttpErrorResponse) => {
        console.error('Error creando item', error);
      }
    );
  }
}