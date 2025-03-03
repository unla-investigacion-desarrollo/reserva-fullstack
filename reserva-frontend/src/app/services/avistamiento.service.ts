import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';

// Definimos la estructura de un avistamiento
interface Avistamiento {
  id: number;
  scientificName: string;
  createdBy: {
    username: string;
  };
  latitude: number;
  longitude: number;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class AvistamientoService {

  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) { }

  // Obtener avistamientos pendientes
  async getAvistamientosPendientes(): Promise<Avistamiento[]> {
    const url = 'http://localhost:8000/sighting?status=pendiente&page=1&size=999999&orderBy=asc&sortBy=id';
    const response = await lastValueFrom(this.http.get<{ data: Avistamiento[] }>(url));
    return response.data;  // Ahora devolvemos directamente el array de avistamientos
  }

  // Obtener avistamientos aprobados
  async getAvistamientosAprobados(): Promise<Avistamiento[]> {
    const url = 'http://localhost:8000/sighting?status=aprobado&page=1&size=999999&orderBy=asc&sortBy=id';
    const response = await lastValueFrom(this.http.get<{ data: Avistamiento[] }>(url));
    return response.data;
  }

  // Obtener avistamientos reprobados
  async getAvistamientosReprobados(): Promise<Avistamiento[]> {
    const url = 'http://localhost:8000/sighting?status=reprobado&page=1&size=999999&orderBy=asc&sortBy=id';
    const response = await lastValueFrom(this.http.get<{ data: Avistamiento[] }>(url));
    return response.data;
  }

  // Obtener un avistamiento por ID
  async getAvistamientoById(id: string): Promise<Avistamiento> {
    const url = 'http://localhost:8000/sighting/' + id;
    return await lastValueFrom(this.http.get<Avistamiento>(url));
  }

  // Actualizar el estado del avistamiento
  async updateStatusAvistamiento(userId: number, sightingId: number, status: string) {
    const url = 'http://localhost:8000/sighting/status';
    const body = {
      idSighting: sightingId,
      approvedById: userId,
      status: status
    };
    return await lastValueFrom(this.http.post(url, body, { headers: this.headers }));
  }

  // Eliminar un avistamiento
  async deleteAvistamiento(avistamientoId: number) {
    const url = 'http://localhost:8000/sighting/delete/' + avistamientoId;
    return await lastValueFrom(this.http.delete(url));
  }
}
