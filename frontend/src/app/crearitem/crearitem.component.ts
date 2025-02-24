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
  nuevoItem: any = {
    descripcion: '',
    peso: null,
    gradosConsecucion: null,
    pruebaId: null,
    especialidadId: null
  };

  items: any[] = []; 
  pruebaId: number | null = null; 

  constructor(private itemService: ItemService) {}

  ngOnInit() {
    const especialidadIdStr = sessionStorage.getItem('especialidadId');
    const pruebaIdStr = sessionStorage.getItem('pruebaId'); 

    if (especialidadIdStr && pruebaIdStr) {
      this.nuevoItem.especialidadId = parseInt(especialidadIdStr, 10);
      this.pruebaId = parseInt(pruebaIdStr, 10);
      this.nuevoItem.pruebaId = this.pruebaId;
    } else {
      console.error('Especialidad o prueba no encontrada en sessionStorage');
    }
  }

  anadirItem() {
    if (!this.nuevoItem.descripcion || !this.nuevoItem.peso || !this.nuevoItem.gradosConsecucion) {
      alert('Por favor complete todos los campos antes de añadir el ítem.');
      return;
    }

    this.items.push({ ...this.nuevoItem });
    this.nuevoItem.descripcion = '';
    this.nuevoItem.peso = null;
    this.nuevoItem.gradosConsecucion = null;
  }

  eliminarItem(index: number) {
    this.items.splice(index, 1);
  }

  guardarTodosLosItems() {
    if (this.items.length === 0) {
      alert('No hay ítems para guardar.');
      return;
    }

    this.itemService.crearVariosItems(this.items).subscribe(
      (response) => {
        console.log('Ítems creados exitosamente', response);
        this.items = []; 
      },
      (error: HttpErrorResponse) => {
        console.error('Error guardando ítems', error);
      }
    );
  }
}