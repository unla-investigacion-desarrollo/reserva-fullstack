import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AvistamientoPendienteComponent } from './components/avistamiento-pendiente/avistamiento-pendiente.component';
import { AvistamientoAprobadoComponent } from './components/avistamiento-aprobado/avistamiento-aprobado.component';
import { HomeComponent } from './components/home/home.component';

import { AppComponent } from './app.component';

const routes: Routes = [
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  { path: 'avistamientos_pendientes', component: AvistamientoPendienteComponent },
  { path: 'avistamientos_aprobados', component: AvistamientoAprobadoComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { } 
