import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { tap, catchError, map } from 'rxjs/operators';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private router: Router) {}

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
    return this.http.post<{success: boolean}>(`${this.apiUrl}/forgot-password`, { email })
      .pipe(
        catchError(() => of({success: false})),
        map(response => response.success)
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