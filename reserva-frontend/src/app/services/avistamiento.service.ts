import { Injectable } from '@angular/core';
import { LoremIpsum, loremIpsum } from "lorem-ipsum";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AvistamientoService {

  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  constructor(private http: HttpClient) { }

  async getAvistamientosPendientes() {

    const url = 'http://localhost:8000/sighting?status=pendiente&page=1&size=999999&orderBy=asc&sortBy=id';

    return await lastValueFrom(this.http.get(url));
  }

  async getAvistamientosAprobados() {

    const url = 'http://localhost:8000/sighting?status=aprobado&page=1&size=999999&orderBy=asc&sortBy=id';

    return await lastValueFrom(this.http.get(url));
  }

  async getAvistamientosReprobados() {

    const url = 'http://localhost:8000/sighting?status=reprobado&page=1&size=999999&orderBy=asc&sortBy=id';

    return await lastValueFrom(this.http.get(url));
  }

  async getAvistamientoById(id: string) {

    const url = 'http://localhost:8000/sighting/' + id;

    return await lastValueFrom(this.http.get(url));
  }

  async getCategorias() {

    const url = 'http://localhost:8000/sighting/type/getTiposAvistamientos';

    return await lastValueFrom(this.http.get(url));
  }

  async updateStatusAvistamiento( userId: number, sightingId: number, status: string) {

    const url = 'http://localhost:8000/sighting/status';

    const body = {
      idSighting: sightingId,
      approvedById: userId,
      status: status
    };

    return await lastValueFrom(this.http.post(url, body, { headers: this.headers }));
  }

  async updateAvistamiento( sightingId: number, userId: number, name: number, scientificName: string, latitude: number, longitude: number, type: string ) {

    const url = 'http://localhost:8000/sighting/update/' + sightingId; //ENDPOINT SWAGGER

    const body = {
      userId: userId,
      name: name,
      scientificName: scientificName,
      latitude: latitude,
      longitude: longitude,
      type: type
    };

    return await lastValueFrom(this.http.post(url, body, { headers: this.headers }));
  }

  async deleteAvistamiento(avistamientoId){
    const url = 'http://localhost:8000/sighting/delete/' + avistamientoId;

    return await lastValueFrom(this.http.delete(url));

  }

  async getAvistamientosMapa() {

    const url = 'http://localhost:8000/sighting/getAllForMap';

    return await lastValueFrom(this.http.get(url));
  }
}
