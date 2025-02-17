import { Component } from '@angular/core';
import { AuthService } from '../auth.service'; 
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';  // Importar ReactiveFormsModule y FormBuilder

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [CommonModule, ReactiveFormsModule]  // Asegúrate de importar ReactiveFormsModule
})
export class LoginComponent {
  loginForm: FormGroup;  // Declarar el formulario reactivo
  errorMessage: string = '';
  username: any;
  password: any;

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {

    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],  // Validación de campo requerido
      password: ['', [Validators.required]]   // Validación de campo requerido
    });
  }

  login(): void {
    if (this.loginForm.invalid) {
      return;
    }
  
    const credentials = this.loginForm.value;
    this.authService.login(credentials).subscribe(
      (response) => {
        // Guardar el token en localStorage
        localStorage.setItem('token', response.token);
  
        // Depurar: mostrar la información decodificada del token
        const userData = this.authService.getUserData();
        console.log('Token decodificado:', userData);
  
        // Redirigir según el rol
        if (this.authService.isAdmin()) {
          this.router.navigate(['/especialidades']);
        } else if (this.authService.isExperto()) {
          this.router.navigate(['/home']);
        } else {
          this.router.navigate(['/home']);
        }
      },
      (error) => {
        this.errorMessage = 'Credenciales incorrectas';
      }
    );
    
}
}
