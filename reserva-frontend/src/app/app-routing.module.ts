import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AvistamientoPendienteComponent } from './components/avistamiento-pendiente/avistamiento-pendiente.component';
import { AvistamientoAprobadoComponent } from './components/avistamiento-aprobado/avistamiento-aprobado.component';
import { AvistamientoEditarComponent } from './components/avistamiento-editar/avistamiento-editar.component';
import { AvistamientoReprobadoComponent } from './components/avistamiento-reprobado/avistamiento-reprobado.component';
import { EditAvistamientoComponent } from './components/edit-avistamiento/edit-avistamiento.component';
import { MapComponentComponent } from './components/map-component/map-component.component';
import { LoginComponent } from './components/login/login.component';
import { HttpClientModule } from '@angular/common/http'; // Import HttpClientModule
import { HomeComponent } from './components/home/home.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { DatePipe } from '@angular/common';
import { authGuard } from './services/auth-guard';
import { VerAvistamientoComponent } from './components/avistamiento-ver/ver-avistamiento.component';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'home', component: HomeComponent, canActivate: [authGuard] },
  { path: 'avistamientos_pendientes', component: AvistamientoPendienteComponent, canActivate: [authGuard] },
  { path: 'avistamientos_aprobados', component: AvistamientoAprobadoComponent, canActivate: [authGuard] },
  { path: 'avistamiento_editar/:id', component: AvistamientoEditarComponent, canActivate: [authGuard] },
  { path: 'avistamientos_reprobados', component: AvistamientoReprobadoComponent, canActivate: [authGuard] },
  { path: 'edit_avistamiento/:id', component: EditAvistamientoComponent, canActivate: [authGuard] },
  { path: 'ver_avistamiento/:id', component: VerAvistamientoComponent, canActivate: [authGuard] },
  { path: 'map', component: MapComponentComponent, canActivate: [authGuard] },
  { path: 'login', component: LoginComponent },

];

@NgModule({
  imports: [RouterModule.forRoot(routes),
    HttpClientModule,
    BrowserAnimationsModule,
    MatTooltipModule],
  exports: [RouterModule],
  providers: [
    DatePipe 
  ]
})

export class AppRoutingModule { } 
