import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';

@Component({
  selector: 'app-map-component',
  templateUrl: './map-component.component.html',
  styleUrls: ['./map-component.component.css']
})

export class MapComponentComponent {
  points: { x: number, y: number, status: string }[] = [];

  latTopLeft = -34.778991;
  longTopLeft = -58.437050;
  latTopRight = -34.783689;
  longTopRight = -58.433513;
  latBottomLeft = -34.779984;
  longBottomLeft = -58.438908;
  latBottomRight = -34.784489;
  longBottomRight = -58.435327;

  coordinates = [
    { lat: -34.7792, long: -58.4360 },
    { lat: -34.7795, long: -58.4352 },
    // Añade más coordenadas aquí
  ];

  avistamientos: any;

  constructor(private avistamientoService: AvistamientoService) {}


  async ngOnInit() {

    this.avistamientos = await this.avistamientoService.getAvistamientosMapa();

    // this.coordinates.forEach(coord => {
    //   const point = this.calculatePoint(coord.lat, coord.long);
    //   this.points.push(point);
    // });

    this.avistamientos.forEach(coord => {
      const point = this.calculatePoint(coord.latitude, coord.longitude, coord.status);
      this.points.push(point);
    });
  }

  onMapClick(event: MouseEvent) {
    const mapElement = event.target as HTMLElement;
    const rect = mapElement.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    const lat = this.calculateLatitude(y, rect.height);
    const long = this.calculateLongitude(x, rect.width);

    console.log(`Latitud: ${lat}, Longitud: ${long}`);
  }

  calculatePoint(lat: number, long: number, status:string): { x: number, y: number, status:string } {
    const width = 1305; // Ancho de la imagen en píxeles
    const height = 500; // Alto de la imagen en píxeles

    const x = ((long - this.longTopLeft) / (this.longTopRight - this.longTopLeft)) * width;
    const y = ((lat - this.latTopLeft) / (this.latBottomLeft - this.latTopLeft)) * height;

    return { x, y, status };
  }

  calculateLatitude(y: number, height: number): number {
    const latRange = this.latBottomLeft - this.latTopLeft;
    return this.latTopLeft + (y / height) * latRange;
  }

  calculateLongitude(x: number, width: number): number {
    const longRange = this.longTopRight - this.longTopLeft;
    return this.longTopLeft + (x / width) * longRange;
  }
}
