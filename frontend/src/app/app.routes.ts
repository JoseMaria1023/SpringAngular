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
//import { ParticipanteEditComponent } from './participante-edit/participante-edit.component';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'especialidades', component: EspecialidadesComponent, canActivate: [AdminGuard] },
  { path: 'participantes/lista', component: ParticipanteslistaComponent, canActivate: [ExpertoGuard] },
  { path: 'participante/crear', component: ParticipantescrearComponent,canActivate: [ExpertoGuard]},
  //{ path: 'participante/editar/:id', component: ParticipanteEditComponent },
  { path: 'participantes', component: ParticipantesComponent,canActivate: [ExpertoGuard]}, // Ruta principal para participantes
  { path: '', redirectTo: '/participantes-main', pathMatch: 'full' }
  ]