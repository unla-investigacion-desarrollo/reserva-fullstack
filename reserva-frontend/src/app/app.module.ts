import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AvistamientoPendienteComponent } from './components/avistamiento-pendiente/avistamiento-pendiente.component';
import { RegistroUsuarioComponent } from './components/registro-usuario/registro-usuario.component';
import { UserService } from './services/user.service';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './components/home/home.component';
import { Router } from '@angular/router';
import { AvistamientoAprobadoComponent } from './components/avistamiento-aprobado/avistamiento-aprobado.component';
import { AvistamientoReprobadoComponent } from './components/avistamiento-reprobado/avistamiento-reprobado.component';
import { EditAvistamientoComponent } from './components/edit-avistamiento/edit-avistamiento.component';
import { ImageModalComponent } from './components/image-modal/image-modal.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { DialogComponentComponent } from './components/dialog-component/dialog-component.component';
import { DialogService } from './services/dialog-service.service';
import { LoginComponent } from './components/login/login.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { HttpInterceptorInterceptor } from './http-interceptor.interceptor';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from './services/auth.service';
import { ResetPasswordComponent } from './components/reset-contraseña/reset-password.component';
import { ResetContraseñaFormComponent } from './components/reset-contraseña-form/reset-contraseña-form.component';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { StatisticsComponent } from './statistics/statistics.component';
import { NgApexchartsModule } from 'ng-apexcharts';
import { SightingsChartComponent } from './sightings-chart/sightings-chart.component';
import { SightingsPieComponent } from './sightings-pie/sightings-pie.component';
import { SightingsOverTimeComponent } from './sightings-over-time/sightings-over-time.component';

@NgModule({
  declarations: [
    AppComponent,
    AvistamientoPendienteComponent,
    HomeComponent,
    AvistamientoAprobadoComponent,
    AvistamientoReprobadoComponent,
    EditAvistamientoComponent,
    DialogComponentComponent,
    LoginComponent,
    HeaderComponent,
    ImageModalComponent,
    RegistroUsuarioComponent,
    ResetPasswordComponent,
    ResetContraseñaFormComponent,
    StatisticsComponent,
    SightingsChartComponent,
    SightingsPieComponent,
    SightingsOverTimeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatDialogModule,
    MatIconModule,
    MatButtonModule,
    MatSnackBarModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatFormFieldModule,
    MatCardModule,
    MatProgressSpinnerModule,
    NgApexchartsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorInterceptor,
      multi: true,
      deps: [Router, AuthService]
    },
    DialogService,
    UserService, 
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}