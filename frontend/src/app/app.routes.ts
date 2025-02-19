import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { EspecialidadesComponent } from './especialidades/especialidades.component';
import { AdminGuard } from './auth/admin.guard';
import { ParticipantesComponent } from './participantes/participantes.component';
import { ParticipanteslistaComponent } from './participanteslista/participanteslista.component';
import { ParticipantescrearComponent } from './participantescrear/participantescrear.component';
import { ExpertoGuard } from './auth/experto.guard';
import { ParticipanteeditarComponent } from './participanteeditar/participanteeditar.component';
import { AdminComponent } from './admin/admin.component';
import { CrearexpertoComponent } from './crearexperto/crearexperto.component';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'especialidades', component: EspecialidadesComponent, canActivate: [AdminGuard] },
  { path: 'participantes/lista', component: ParticipanteslistaComponent },
  { path: 'participante/crear', component: ParticipantescrearComponent,canActivate: [ExpertoGuard]},
  {  path: 'participante/editar/:id', component: ParticipanteeditarComponent, canActivate: [ExpertoGuard]},
  { path: 'participantes', component: ParticipantesComponent}, 
  { path: 'admin', component: AdminComponent }, 
  { path: 'crear/experto', component: CrearexpertoComponent }, 
  { path: '', redirectTo: '/participantes-main', pathMatch: 'full' }
  ]