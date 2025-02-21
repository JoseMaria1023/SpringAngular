import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-evaluaciones',
  imports: [],
  templateUrl: './evaluaciones.component.html',
  styleUrl: './evaluaciones.component.css'
})
export class EvaluacionesComponent implements OnInit {
  pruebas: any[] = [];  // Lista de pruebas
  evaluaciones: any[] = [];  // Lista de evaluaciones para la prueba seleccionada
  selectedPruebaId: number | null = null;  // ID de la prueba seleccionada

  constructor() {}

  ngOnInit(): void {
    
  }
}