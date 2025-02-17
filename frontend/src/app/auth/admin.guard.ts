import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const user = this.authService.getUserData(); // Obtener los datos del usuario desde el JWT

    // Cambiado 'admin' por 'ROLE_ADMIN'
    if (user && user.roles.includes('ROLE_ADMIN')) {
      return true; // Permitir acceso solo si es admin
    } else {
      this.router.navigate(['/']); // Redirigir al Home si no es admin
      return false; // Bloquear el acceso
    }
  }
}
