import { Component, OnInit } from '@angular/core';
import { ItemService } from '../item.service';
import { PruebaService } from '../prueba.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

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

  constructor(private itemService: ItemService, private pruebaService: PruebaService) {}

  ngOnInit() {
    const especialidadIdStr = sessionStorage.getItem('especialidadId');
    if (especialidadIdStr) {
      this.nuevoItem.especialidadId = parseInt(especialidadIdStr, 10);
    } else {
      console.error('Especialidad no encontrada en sessionStorage');
    }
  }

  anadirItem() {
    if (!this.nuevoItem.descripcion || !this.nuevoItem.peso || !this.nuevoItem.gradosConsecucion) {
      alert('Por favor complete todos los campos antes de añadir el ítem.');
      return;
    }
    // Agregamos el item (sin borrar pruebaId, ya que se asignará más adelante)
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
    
    // En lugar de depender de sessionStorage, consultamos el último idPrueba desde el backend
    this.pruebaService.obtenerUltimoIdPrueba().subscribe(
      (ultimoId) => {
        console.log('Último idPrueba obtenido:', ultimoId);
        this.pruebaId = ultimoId;
  
        // Asignar pruebaId a cada ítem antes de enviarlos
        this.items.forEach(item => {
          item.pruebaId = this.pruebaId;
        });
  
        // Guardar los ítems en el backend
        this.itemService.crearVariosItems(this.items).subscribe(
          (response) => {
            console.log('Ítems creados exitosamente', response);
            this.items = []; 
          },
          (error: HttpErrorResponse) => {
            console.error('Error guardando ítems', error);
          }
        );
      },
      (error) => {
        console.error('Error obteniendo el último idPrueba', error);
      }
    );
  }
}
