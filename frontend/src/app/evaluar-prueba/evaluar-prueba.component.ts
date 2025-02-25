import { Component, OnInit } from '@angular/core';
import { EvaluacionItemService } from '../evaluacion-item.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-evaluar-prueba',
  imports: [CommonModule, FormsModule],
  templateUrl: './evaluar-prueba.component.html',
  styleUrls: ['./evaluar-prueba.component.css']
})
export class EvaluarPruebaComponent implements OnInit {
  pruebaId: number | null = null;
  enunciado: string = '';
  items: any[] = [];
  evaluaciones: any[] = [];
  idEvaluacion!: number | null;
  mediaPonderada: number = 0;  

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
        this.obtenerIdEvaluacion(); 
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
        this.idEvaluacion = idEvaluacion; 
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
          explicacion: '',
          peso: item.peso 
        }));
      },
      (error) => console.error('Error obteniendo los ítems:', error)
    );
  }

  calcularMediaPonderada() {
    let totalPeso = 0;
    let totalValoracion = 0;
  
    this.evaluaciones.forEach((evaluacion, i) => {
      const peso = this.items[i].peso; 
      const maxGC = this.items[i].gradosConsecucion; 
      const valoracion = evaluacion.valoracion || 0; 
  
      if (maxGC > 0) {
        totalValoracion += (valoracion / maxGC) * peso; 
      }
  
      totalPeso += peso; 
    });
  
    this.mediaPonderada = totalPeso > 0 ? (totalValoracion / totalPeso) * 100 : 0;
  }

  todasLasValoracionesCompletas(): boolean {
    return this.evaluaciones.every(evaluacion => evaluacion.valoracion !== null);
  }
  
  enviarEvaluacion() {
    if (!this.idEvaluacion) {
      alert('No se pudo obtener el ID de evaluación');
      return;
    }
  
    if (!this.todasLasValoracionesCompletas()) {
      alert('Todos los ítems deben ser evaluados.');
      return;
    }
  
    this.calcularMediaPonderada();
  
    const payload = this.evaluaciones.map(evaluacion => ({
      evaluacionId: this.idEvaluacion, 
      itemId: evaluacion.itemId,
      valoracion: evaluacion.valoracion,
      explicacion: evaluacion.explicacion
    }));
  
    this.evaluacionItemService.enviarEvaluacion(payload).subscribe(
      () => {
        this.evaluacionItemService.actualizarNotaFinal(this.idEvaluacion!).subscribe(
          () => {
            alert('Evaluación enviada y nota final actualizada.');
          },
          (error) => console.error('Error actualizando la nota final:', error)
        );
      },
      (error) => console.error('Error enviando la evaluación:', error)
    );  
  }
  
}
