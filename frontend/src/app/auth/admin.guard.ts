import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { AuthService } from '../auth.service';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const token = this.authService.getToken();
    console.log("AdminGuard: token =", token);
    if (token && this.authService.getRoles().includes('ROLE_ADMIN')) {
      console.log("AdminGuard: acceso permitido");
      return true;
    } else {
      console.log("AdminGuard: acceso denegado, redirigiendo a /home");
      this.router.navigate(['/home']);
      return false;
    }
  }
}
