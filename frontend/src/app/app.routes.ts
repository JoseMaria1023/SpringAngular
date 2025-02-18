import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { EspecialidadesComponent } from './especialidades/especialidades.component';
import { AdminGuard } from './auth/admin.guard';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'especialidades', component: EspecialidadesComponent, canActivate: [AdminGuard] }

  ]