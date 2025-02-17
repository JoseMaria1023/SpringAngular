import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, ReactiveFormsModule } from '@angular/forms';
import { NgIf, NgFor, CommonModule } from '@angular/common';
import { AuthService } from '../auth.service'; 

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [ReactiveFormsModule, NgIf, NgFor, CommonModule],

})
export class RegisterComponent {
  registerForm = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)]),
    confirmPassword: new FormControl('', [Validators.required]),
  });

  errorMessage = '';

  constructor(private authService: AuthService) {}

  passwordsMatch(): boolean {
    return this.registerForm.get('password')?.value === this.registerForm.get('confirmPassword')?.value;
  }

  onSubmit() {
    if (this.registerForm.valid && this.passwordsMatch()) {
      const { name, email, password } = this.registerForm.value;

      this.authService.register(name!, email!, password!).subscribe({
        next: () => {
          console.log('Registro exitoso');
        },
        error: (err) => {
          this.errorMessage = 'Error en el registro';
          console.error('Error:', err);
        },
      });
    } else {
      this.errorMessage = 'Las contraseñas no coinciden';
    }
  }
}
