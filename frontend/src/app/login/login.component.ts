import { Component } from '@angular/core';
import { AuthService } from '../auth.service'; 
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms'; 

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [CommonModule, ReactiveFormsModule] 
})
export class LoginComponent {
  loginForm: FormGroup;  
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router, private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]], 
      password: ['', [Validators.required]]   
    });
  }

  login(): void {
    if (this.loginForm.invalid) {
      console.log("LoginComponent: formulario inválido");
      return;
    }
  
    const credentials = this.loginForm.value;
    console.log("LoginComponent: intentando login con", credentials);
    this.authService.login(credentials).subscribe(
      (response) => {
        console.log("LoginComponent: respuesta de login", response);
        // Guarda la sesión (token, username, roles)
        this.authService.setSession(response);
        // Redirige según el rol
        this.authService.redirectUser();
      },
      (error) => {
        console.error("LoginComponent: error en login", error);
        this.errorMessage = 'Credenciales incorrectas';
      }
    );
  }
}
