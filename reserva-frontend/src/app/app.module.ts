import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppComponent } from './app.component';
import { AvistamientoPendienteComponent } from './components/avistamiento-pendiente/avistamiento-pendiente.component';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './components/home/home.component';
import { AvistamientoAprobadoComponent } from './components/avistamiento-aprobado/avistamiento-aprobado.component';
import { AvistamientoReprobadoComponent } from './components/avistamiento-reprobado/avistamiento-reprobado.component';
import { EditAvistamientoComponent } from './components/edit-avistamiento/edit-avistamiento.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { DialogComponentComponent } from './components/dialog-component/dialog-component.component';
import { DialogService } from './services/dialog-service.service';
import { LoginComponent } from './components/login/login.component';
import { HeaderComponent } from './components/shared/header/header.component';
import { HttpInterceptorInterceptor } from './http-interceptor.interceptor';

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
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatDialogModule,
    MatButtonModule
    // Optional: Add FormsModule, ReactiveFormsModule, or other Material modules if needed
    // e.g., FormsModule, ReactiveFormsModule, MatFormFieldModule, MatInputModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpInterceptorInterceptor,
      multi: true
    },
    DialogService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}