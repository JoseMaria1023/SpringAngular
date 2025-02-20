import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listaexperto',
  imports: [CommonModule],
  templateUrl: './listaexperto.component.html',
  styleUrl: './listaexperto.component.css'
})
export class ListaexpertoComponent implements OnInit {
  [x: string]: any;
  users: any[] = [];
  mostrarFormularioCrear: boolean = false;

  constructor(private userService: UserService,private router: Router) {}

  ngOnInit() {
    this.loadExperts();
  }
  editarUsuario(id: number): void {
    this.router.navigate(['/admin/editarexperto', id]);
  }
  irACrearExperto(): void {
    this.router.navigate(['admin/crear/experto']);
  }

  activarCreacion(): void {
    this.mostrarFormularioCrear = true;
  }
  loadExperts() {
    this.userService.loadExperts().subscribe(
      (data) => {
        this.users = data;
      },
    );
    
  }
}