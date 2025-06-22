import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { lastValueFrom, Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { CONFIG } from '../config';

export interface UserResponseDto {
  id: number;
  name: string;
  lastname: string;       // ← Nuevo
  username: string;
  email: string;
  avatarUrl?: string;     // ← Opcional
  role: {                 // ← Nuevo
    id: number;
    name: string;
  };
  createdAt: string;      // ← Nuevo
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = CONFIG.apiUrl;

  constructor(private http: HttpClient) {}

  async register(username: string, name: string, email: string, password: string): Promise<boolean> {
    try {
      const response = await lastValueFrom(
        this.http.post<{ success: boolean }>(`${this.apiUrl}/users/register`, { username, name, email, password })
      );
      return response.success;
    } catch (error) {
      console.error('Registration API error:', error);
      throw error;
    }
  }

  getCurrentUser(): Observable<UserResponseDto> {
    return this.http.get<UserResponseDto>(`${this.apiUrl}/users/me`, {
      headers: new HttpHeaders({
        'Authorization': `Bearer ${localStorage.getItem('auth_token')}`
      })
    }).pipe(
      catchError(error => {
        console.error('Error al cargar usuario:', error);
        throw error; // Opcional: Puedes devolver un objeto de usuario vacío como fallback
      })
    );
  }

}