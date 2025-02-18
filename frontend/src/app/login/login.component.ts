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
      return;
    }
  
    const credentials = this.loginForm.value;
    this.authService.login(credentials).subscribe(
      (response) => {
        this.authService.setSession(response);
        this.authService.redirectUser();
      },
      (error) => {
        this.errorMessage = 'Credenciales incorrectas';
      }
    );
  }
}
