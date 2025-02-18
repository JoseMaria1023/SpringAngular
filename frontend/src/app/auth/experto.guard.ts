import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class ExpertoGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const token = this.authService.getToken();
    console.log("ExpertoGuard: token =", token);
    if (token && this.authService.getRoles().includes('ROLE_EXPERTO')) {
      console.log("ExpertoGuard: acceso permitido");
      return true;
    } else {
      console.log("ExpertoGuard: acceso denegado, redirigiendo a /home");
      this.router.navigate(['/home']);
      return false;
    }
  }
}
