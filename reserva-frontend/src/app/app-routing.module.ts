import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AvistamientoPendienteComponent } from './components/avistamiento-pendiente/avistamiento-pendiente.component';
import { AvistamientoAprobadoComponent } from './components/avistamiento-aprobado/avistamiento-aprobado.component';
import { AvistamientoReprobadoComponent } from './components/avistamiento-reprobado/avistamiento-reprobado.component';
import { EditAvistamientoComponent } from './components/edit-avistamiento/edit-avistamiento.component';
import { LoginComponent } from './components/login/login.component';

import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/shared/header/header.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'avistamientos_pendientes', component: AvistamientoPendienteComponent },
  { path: 'avistamientos_aprobados', component: AvistamientoAprobadoComponent },
  { path: 'avistamientos_reprobados', component: AvistamientoReprobadoComponent },
  { path: 'edit_avistamiento/:id', component: EditAvistamientoComponent },
  { path: 'login', component: LoginComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { } 
