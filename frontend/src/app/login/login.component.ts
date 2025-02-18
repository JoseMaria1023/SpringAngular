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
  username: any;
  password: any;

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
        localStorage.setItem('token', response.token);
  
        const userData = this.authService.getUserData();
        console.log('Token decodificado:', userData);
  
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
