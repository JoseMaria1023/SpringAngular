import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EvaluacionService } from '../evaluacion.service';
import { AuthService } from '../auth.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-realizar-evaluacion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './realizar-evaluacion.component.html',
  styleUrls: ['./realizar-evaluacion.component.css']
})
export class RealizarEvaluacionComponent implements OnInit {
  evaluacion: any = {
    participanteId: null,
    pruebaId: null,
    userId: null,
    notaFinal: null
  };

  constructor(
    private evaluacionService: EvaluacionService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['participanteId']) {
        this.evaluacion.participanteId = +params['participanteId'];
      }
      if (params['pruebaId']) {
        this.evaluacion.pruebaId = +params['pruebaId'];
      }
    });

    const userId = this.authService.getUserId();
    if (userId) {
      this.evaluacion.userId = userId;
    } 
  }

  onSubmit(): void {
    if (this.evaluacion.notaFinal == null) {
      alert('Por favor, ingresa la nota final.');
      return;
    }
    this.evaluacionService.evaluarParticipante(this.evaluacion).subscribe(
      response => {
        alert('Evaluación guardada con éxito.');

      },
      error => {
        console.error('Error al guardar la evaluación:', error);
        alert('Error al guardar la evaluación.');
      }
    );
  }
}
