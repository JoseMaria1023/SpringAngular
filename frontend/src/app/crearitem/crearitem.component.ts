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
    pruebaId: null,
    especialidadId: null  
  };

  especialidades: any[] = [];
  pruebas: any[] = []; 

  constructor(
    private itemService: ItemService,
    private especialidadService: EspecialidadService,
    private pruebaService: PruebaService
  ) {}

  ngOnInit() {
    this.especialidadService.getEspecialidades().subscribe(
      (data) => {
        this.especialidades = data;
      },
      (error) => {
        console.error('Error cargando especialidades', error);
      }
    );

    const especialidadIdStr = sessionStorage.getItem('especialidadId');
    if (especialidadIdStr) {
      this.item.especialidadId = parseInt(especialidadIdStr, 10);

      this.pruebaService.traerPruebasPorEspecialidad(this.item.especialidadId).subscribe(
        (data) => {
          this.pruebas = data;
        },
        (error) => {
          console.error('Error cargando pruebas por especialidad', error);
        }
      );
    } else {
      console.error('Especialidad no encontrada en sessionStorage');
    }
  }

  onSubmit() {
    if (!this.item.pruebaId) {
      return;
    }
    this.itemService.crearItem(this.item).subscribe(
      (response) => {
        console.log('Item creado exitosamente', response);
      },
      (error: HttpErrorResponse) => {
        console.error('Error creando item', error);
      }
    );
  }
}