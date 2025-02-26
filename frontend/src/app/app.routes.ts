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
import { PruebaComponent } from './prueba/prueba.component';
import { CrearitemComponent } from './crearitem/crearitem.component';
import { ListapruebaComponent } from './listaprueba/listaprueba.component';
import { RealizarEvaluacionComponent } from './realizar-evaluacion/realizar-evaluacion.component';
import { EditarpruebaComponent } from './editarprueba/editarprueba.component';
import { CrearpruebaComponent } from './crearprueba/crearprueba.component';
import { EvaluarPruebaComponent } from './evaluar-prueba/evaluar-prueba.component';
import { ExpertoComponent } from './experto/experto.component';
import { ListaEvaluacionesComponent } from './lista-evaluaciones/lista-evaluaciones.component';

export const appRoutes: Routes = [
  { path: '', component: HomeComponent, pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomeComponent },
  { path: 'register', component: RegisterComponent },  
  { path: 'especialidad', component: ListarespecialidadComponent},
  { path: 'admin/especialidad/crear', component: CreaespecialidadComponent, canActivate: [AdminGuard] },
  { path: 'admin/especialidad/editar/:id', component: EditarespecialidadComponent, canActivate: [AdminGuard] },
  { path: 'participantes/lista', component: ParticipanteslistaComponent },
  { path: 'experto/participante/crear', component: ParticipantescrearComponent, canActivate: [ExpertoGuard] },
  { path: 'experto/participantes', component: ParticipantesComponent, canActivate: [ExpertoGuard] },
  { path: 'experto/participante/editar/:id', component: ParticipanteeditarComponent, canActivate: [ExpertoGuard] },
  { path: 'participantes', component: ParticipantesComponent },
  { path: 'admin', component: AdminComponent, canActivate: [AdminGuard] },
  { path: 'admin/crear/experto', component: CrearexpertoComponent, canActivate: [AdminGuard] },
  { path: 'admin/listar/experto', component: ListaexpertoComponent, canActivate: [AdminGuard] },
  { path: 'admin/editarexperto/:id', component: EditarexpertoComponent },
  { path: 'experto/pruebas', component: PruebaComponent, canActivate: [ExpertoGuard] },
  { path: 'experto/items', component: CrearitemComponent, canActivate: [ExpertoGuard] },
  { path: 'experto/listarpruebas', component: ListapruebaComponent, canActivate: [ExpertoGuard] },
  { path: 'experto/realizar-evaluacion', component: RealizarEvaluacionComponent, canActivate: [ExpertoGuard] },
  { path: 'experto/editar-prueba/:id', component: EditarpruebaComponent, canActivate: [ExpertoGuard]},
  { path: 'experto/crear-prueba', component: CrearpruebaComponent, canActivate: [ExpertoGuard] },
  { path: 'experto/evaluar-prueba/:idPrueba', component: EvaluarPruebaComponent, canActivate: [ExpertoGuard]},
  { path : 'experto', component: ExpertoComponent, canActivate: [ExpertoGuard]},
  { path : 'admin/lista-evaluacion', component: ListaEvaluacionesComponent, canActivate: [AdminGuard]}


  











];
