import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AvistamientoPendienteComponent } from './components/avistamiento-pendiente/avistamiento-pendiente.component';
import { AvistamientoAprobadoComponent } from './components/avistamiento-aprobado/avistamiento-aprobado.component';
import { AvistamientoReprobadoComponent } from './components/avistamiento-reprobado/avistamiento-reprobado.component';
import { EditAvistamientoComponent } from './components/edit-avistamiento/edit-avistamiento.component';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule } from '@angular/common/http'; // Import HttpClientModule
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { DatePipe } from '@angular/common';
import { RegistroUsuarioComponent } from './components/registro-usuario/registro-usuario.component';
import { ResetPasswordComponent } from './components/reset-contraseña/reset-password.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { ResetContraseñaFormComponent } from './components/reset-contraseña-form/reset-contraseña-form.component';
import { StatisticsComponent } from './statistics/statistics.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'avistamientos_pendientes', component: AvistamientoPendienteComponent },
  { path: 'avistamientos_aprobados', component: AvistamientoAprobadoComponent },
  { path: 'avistamientos_reprobados', component: AvistamientoReprobadoComponent },
  { path: 'edit_avistamiento/:id', component: EditAvistamientoComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistroUsuarioComponent },
  { path: 'forgot-password', component: ResetPasswordComponent },
  { path: 'mi-perfil', component: UserProfileComponent},
  { path: 'estadisticas', component: StatisticsComponent},
  { path: 'account/reset-password', component: ResetContraseñaFormComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes),HttpClientModule],
  exports: [RouterModule],
  providers: [
    DatePipe 
  ]
})

export class AppRoutingModule { } 
