import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user.service';

@Component({
  selector: 'app-editarexperto',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './editarexperto.component.html',
  styleUrl: './editarexperto.component.css'
})
export class EditarexpertoComponent {
  experto: any = {
    idUser: null,
    nombre: '',
    apellidos: '',
    dni: '',
    especialidadId: null,
  };

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.userService.getUserById(id).subscribe((data) => {
        this.experto = data;
      });
    }
  }

  actualizarExperto() {
    this.userService.updateUser(this.experto).subscribe(() => {
      alert('Experto actualizado correctamente');
      this.router.navigate(['/lista-expertos']);
    });
  }
}
