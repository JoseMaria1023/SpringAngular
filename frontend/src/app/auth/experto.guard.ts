import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class ExpertoGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const user = this.authService.getUserData(); // Obtener los datos del usuario desde el JWT

    if (user && user.roles.includes('experto')) {
      return true; // Permitir acceso solo si es experto
    } else {
      this.router.navigate(['/']); // Redirigir al Home si no es experto
      return false; // Bloquear el acceso
    }
  }
}
