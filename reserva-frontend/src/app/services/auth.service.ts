import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { tap, catchError, map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { Observable, throwError, TimeoutError, of } from 'rxjs';
import { timeout, retry } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8000/api/auth';
  private baseUrl = 'http://localhost:8000';

  constructor(private http: HttpClient, private router: Router) { }

  // Login
  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, { username, password }).pipe(
      tap((response: any) => {
        localStorage.setItem('auth_token', response.token);
      })
    );
  }

  // Registro
  register(username: string, name: string, email: string, password: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, {
      username,
      name,
      email,
      password
    });
  }

  // Método para recuperación de contraseña
  sendPasswordResetEmail(email: string): Observable<boolean> {
    return this.http.post<{ success: boolean }>(
      `${this.baseUrl}/account/recovery`, // ← Usa baseUrl para este endpoint
      { email }
    ).pipe(
      catchError(() => of({ success: false })),
      map(response => response.success)
    );
  }

  resetPassword(token: string, password: string, passwordRepeat: string): Observable<any> {
    const body = {
      token: token.trim(),
      password: password,
      passwordRepeat: passwordRepeat
    };

    // Usa baseUrl para mantener consistencia con otros métodos
    return this.http.post(`${this.baseUrl}/account/reset-password`, body, {
      headers: new HttpHeaders({
        'Content-Type': 'application/json'
      })
    }).pipe(
      catchError(error => {
        console.error('Error en resetPassword:', {
          status: error.status,
          url: error.url,
          error: error.error
        });

        let errorMsg = 'Error al restablecer contraseña';

        if (error.status === 0) {
          errorMsg = 'Error de conexión con el servidor';
        } else if (error.status === 401) {
          errorMsg = error.error?.message || 'Token inválido o expirado';
        } else if (error.status === 404) {
          errorMsg = 'Endpoint no encontrado. Verifica la configuración del backend';
        }

        return throwError(() => ({
          message: errorMsg,
          status: error.status,
          serverError: error.error
        }));
      })
    );
  }

  // Verificar autenticación
  isAuthenticated(): boolean {
    return !!localStorage.getItem('auth_token');
  }

  // Logout
  logout(): void {
    localStorage.removeItem('auth_token+');
    localStorage.removeItem('userData');
    this.router.navigate(['/login']);
  }

  // Manejo de errores
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}


