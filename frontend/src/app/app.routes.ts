import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ListarespecialidadComponent } from './listarespecialidad/listarespecialidad.component';
import { CreaespecialidadComponent } from './creaespecialidad/creaespecialidad.component';
import { AdminGuard } from './auth/admin.guard';
import { ParticipantesComponent } from './participantes/participantes.component';
import { ParticipanteslistaComponent } from './participanteslista/participanteslista.component';
import { ParticipantescrearComponent } from './participantescrear/participantescrear.component';
import { ParticipanteeditarComponent } from './participanteeditar/participanteeditar.component';
import { AdminComponent } from './admin/admin.component';
import { CrearexpertoComponent } from './crearexperto/crearexperto.component';
import { ExpertoGuard } from './auth/experto.guard';
import { EditarespecialidadComponent } from './editarespecialidad/editarespecialidad.component';
import { ListaexpertoComponent } from './listaexperto/listaexperto.component';
import { EditarexpertoComponent } from './editarexperto/editarexperto.component';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },  
  { path: 'admin/especialidad', component: ListarespecialidadComponent, canActivate: [AdminGuard] },
  { path: 'admin/especialidad/crear', component: CreaespecialidadComponent, canActivate: [AdminGuard] },
  { path: 'admin/especialidad/editar/:id', component: EditarespecialidadComponent, canActivate: [AdminGuard] },
  { path: 'participantes/lista', component: ParticipanteslistaComponent },
  { path: 'experto/participante/crear', component: ParticipantescrearComponent, canActivate: [ExpertoGuard] },
  { path: 'experto/participante/editar/:id', component: ParticipanteeditarComponent, canActivate: [ExpertoGuard] },
  { path: 'participantes', component: ParticipantesComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'admin/crear/experto', component: CrearexpertoComponent, canActivate: [AdminGuard] },
  { path: 'admin/listar/experto', component: ListaexpertoComponent, canActivate: [AdminGuard] },
  { path: 'admin/editarexperto/:id', component: EditarexpertoComponent }

];
