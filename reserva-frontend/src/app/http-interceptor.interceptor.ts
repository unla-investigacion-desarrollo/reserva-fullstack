import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { AuthService } from './services/auth.service';

@Injectable()
export class HttpInterceptorInterceptor implements HttpInterceptor {

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    // Clonar la request para modificarla
    let authReq = request;
    
    // Obtener el token según tu estructura actual
    const userData = localStorage.getItem('userData');
    const token = userData ? JSON.parse(userData).data.accessToken : null;

    // Adjuntar token si existe
    if (token) {
      authReq = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    // Manejar la respuesta
    return next.handle(authReq).pipe(
      catchError((error: HttpErrorResponse) => {
        // Manejo de errores HTTP
        if (error.status === 401) {
          this.handleUnauthorized();
        } else if (error.status === 403) {
          this.handleForbidden();
        }
        return throwError(error);
      })
    );
  }

  private handleUnauthorized(): void {
    // Limpiar datos de autenticación
    localStorage.removeItem('userData');
    this.authService.logout(); // Usar el servicio para limpieza centralizada
    
    // Redirigir a login con parámetro
    this.router.navigate(['/login'], { 
      queryParams: { 
        sessionExpired: true 
      } 
    });
  }

  private handleForbidden(): void {
    this.router.navigate(['/forbidden']); // Ruta para acceso denegado
  }
}