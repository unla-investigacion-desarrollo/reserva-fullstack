import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';
import { Observable } from 'rxjs';

interface Avistamiento {
  id: number;
  scientificName: string;
  createdBy: {
    username: string;
  };
  latitude: number;
  longitude: number;
  createdAt: string;
  images?: ImagenAvistamiento[]; // Añadido para soportar imágenes embebidas
}

interface ImagenAvistamiento {
  id: number;
  url: string; // Esta es la URL relativa que usaremos con /storage/{url}
  sighting_id: number;
}

@Injectable({
  providedIn: 'root'
})
export class AvistamientoService {
  private apiUrl = 'http://localhost:8000';
  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) { }

  // Método para obtener la URL completa de la imagen
  getFullImageUrl(relativeUrl: string): string {
    return `${this.apiUrl}/storage/${encodeURIComponent(relativeUrl)}`;
  }

  // Obtener avistamientos pendientes (actualizado para manejar imágenes)
  async getAvistamientosPendientes(): Promise<Avistamiento[]> {
    const url = `${this.apiUrl}/sighting?status=pendiente&page=1&size=999999&orderBy=asc&sortBy=id`;
    const response = await lastValueFrom(this.http.get<{ data: Avistamiento[] }>(url));
    return response.data.map(avist => ({
      ...avist,
      images: avist.images?.map(img => ({
        ...img,
        url: this.getFullImageUrl(img.url) // Convertir a URL completa
      }))
    }));
  }

  // Obtener imágenes de un avistamiento (versión mejorada)
 async getImagesBySightingId(sightingId: number): Promise<{id: number, url: string}[]> {
  //const url = `${this.apiUrl}/sighting/${sightingId}/images`;
  const url = `${this.apiUrl}/api/sightings/${sightingId}/images`;
  const response = await lastValueFrom(this.http.get<{ data: any[] }>(url));
  
  // Asegúrate de que las URLs sean absolutas y válidas
  return response.data.map(img => ({
    id: img.id,
    url: `${this.apiUrl}/storage/${img.url.replace(/^\/|\/$/g, '')}` // Elimina barras al inicio/final
  }));
}

  // Obtener un avistamiento por ID (actualizado para manejar imágenes)
  async getAvistamientoById(id: string): Promise<Avistamiento> {
    const url = `${this.apiUrl}/sighting/${id}`;
    const avistamiento = await lastValueFrom(this.http.get<Avistamiento>(url));
    return {
      ...avistamiento,
      images: avistamiento.images?.map(img => ({
        ...img,
        url: this.getFullImageUrl(img.url)
      }))
    };
  }

  // ... (mantenemos los demás métodos sin cambios)
  async getAvistamientosAprobados(): Promise<Avistamiento[]> {
    const url = `${this.apiUrl}/sighting?status=aprobado&page=1&size=999999&orderBy=asc&sortBy=id`;
    const response = await lastValueFrom(this.http.get<{ data: Avistamiento[] }>(url));
    return response.data;
  }

  async getAvistamientosReprobados(): Promise<Avistamiento[]> {
    const url = `${this.apiUrl}/sighting?status=reprobado&page=1&size=999999&orderBy=asc&sortBy=id`;
    const response = await lastValueFrom(this.http.get<{ data: Avistamiento[] }>(url));
    return response.data;
  }

  async updateStatusAvistamiento(userId: number, sightingId: number, status: string) {
    const url = `${this.apiUrl}/sighting/status`;
    const body = {
      idSighting: sightingId,
      approvedById: userId,
      status: status
    };
    return await lastValueFrom(this.http.post(url, body, { headers: this.headers }));
  }

  async deleteAvistamiento(avistamientoId: number) {
    const url = `${this.apiUrl}/sighting/delete/${avistamientoId}`;
    return await lastValueFrom(this.http.delete(url));
  }

  getImageUrl(imageRef: string | number): string {
    return `${this.apiUrl}/storage/${encodeURIComponent(imageRef)}`;
  }

  getImageUrlsBySighting(sightingId: number): Observable<string[]> {
    return this.http.get<string[]>(
      `${this.apiUrl}/storage/by-sighting/${sightingId}`
    );
  }

  async getAvistamientoConImagenes(id: string): Promise<any> {
    return lastValueFrom(
      this.http.get<{ imagenes: {id: number, url: string}[] }>(
        `${this.apiUrl}/sighting/${id}`
      )
    );
  }

  getSightingsByUser(userId: number): Observable<Avistamiento[]> {
  return this.http.get<Avistamiento[]>(`${this.apiUrl}/sighting/user/${userId}`);
}
}