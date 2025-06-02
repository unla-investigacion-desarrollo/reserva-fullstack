import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';
import { CONFIG } from '../config';

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
}