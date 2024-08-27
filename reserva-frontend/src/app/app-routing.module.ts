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
import { authGuard } from './services/auth-guard';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'avistamientos_pendientes', component: AvistamientoPendienteComponent, canActivate: [authGuard] },
  { path: 'avistamientos_aprobados', component: AvistamientoAprobadoComponent, canActivate: [authGuard] },
  { path: 'avistamientos_reprobados', component: AvistamientoReprobadoComponent, canActivate: [authGuard] },
  { path: 'edit_avistamiento/:id', component: EditAvistamientoComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes),HttpClientModule],
  exports: [RouterModule],
  providers: [
    DatePipe 
  ]
})

export class AppRoutingModule { } 
