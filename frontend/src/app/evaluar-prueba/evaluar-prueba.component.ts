import { Component, OnInit } from '@angular/core';
import { EvaluacionService } from '../evaluacion.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EvaluacionItemService } from '../evaluacion-item.service';

@Component({
  selector: 'app-evaluar-prueba',
  imports: [CommonModule,FormsModule],
  templateUrl: './evaluar-prueba.component.html',
  styleUrl: './evaluar-prueba.component.css'
})
export class EvaluarPruebaComponent implements OnInit {
  pruebaId: number | null = null;
  enunciado: string = '';
  items: any[] = [];
  evaluaciones: any[] = [];
  idEvaluacion!: number | null;

  constructor(
    private evaluacionItemService: EvaluacionItemService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      const id = params.get('idPrueba');
      if (id) {
        this.pruebaId = parseInt(id, 10);
        this.obtenerEnunciadoDePrueba();
        this.obtenerItemsDePrueba();
        this.obtenerIdEvaluacion(); // 🔥 Nuevo método
      }
    });
  }

  obtenerEnunciadoDePrueba() {
    if (!this.pruebaId) return;
    this.evaluacionItemService.obtenerEnunciadoDePrueba(this.pruebaId).subscribe(
      (data) => {
        this.enunciado = data;
      },
      (error) => console.error('Error obteniendo el enunciado:', error)
    );
  }

  obtenerIdEvaluacion() {
    if (!this.pruebaId) return;
    this.evaluacionItemService.obtenerIdEvaluacionPorPrueba(this.pruebaId).subscribe(
      (idEvaluacion) => {
        this.idEvaluacion = idEvaluacion; // Guardamos el ID de evaluación
      },
      (error) => console.error('Error obteniendo ID de evaluación:', error)
    );
  }
  obtenerItemsDePrueba() {
    if (!this.pruebaId) return;
    this.evaluacionItemService.obtenerItemsDePrueba(this.pruebaId).subscribe(
      (data) => {
        this.items = data;
        this.evaluaciones = this.items.map(item => ({
          itemId: item.idItem,
          valoracion: null,
          explicacion: ''
        }));
      },
      (error) => console.error('Error obteniendo los ítems:', error)
    );
  }

  enviarEvaluacion() {
    if (!this.idEvaluacion) {
      alert('No se pudo obtener el ID de evaluación');
      return;
    }
  
    if (this.evaluaciones.some(e => e.valoracion === null)) {
      alert('Todos los ítems deben ser evaluados.');
      return;
    }
  
    const payload = this.evaluaciones.map(evaluacion => ({
      evaluacionId: this.idEvaluacion, // Usamos el ID de evaluación
      itemId: evaluacion.itemId,
      valoracion: evaluacion.valoracion,
      explicacion: evaluacion.explicacion
    }));
  
    this.evaluacionItemService.enviarEvaluacion(payload).subscribe(
      () => {
        alert('Evaluación enviada correctamente.');
      },
      (error) => console.error('Error enviando la evaluación:', error)
    );  
  }
}