import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';

@Component({
  selector: 'app-map-component',
  templateUrl: './map-component.component.html',
  styleUrls: ['./map-component.component.css']
})

export class MapComponentComponent {
  points: { x: number, y: number, status: string, id:number, isVisible:boolean, description: string, title: string }[] = [];

  latTopLeft = -34.778991;
  longTopLeft = -58.437050;
  latTopRight = -34.783689;
  longTopRight = -58.433513;
  latBottomLeft = -34.779984;
  longBottomLeft = -58.438908;
  latBottomRight = -34.784489;
  longBottomRight = -58.435327;

  avistamientos: any;
  locationInfo: { title: string, description: string } | null = null;

  constructor(private avistamientoService: AvistamientoService) {}

  async ngOnInit() {

    this.avistamientos = await this.avistamientoService.getAvistamientosMapa();
    this.avistamientos.forEach(avist => {
      const point = this.calculatePoint(avist.latitude, avist.longitude, avist.status, avist.id, false, avist.fields[0].description, avist.fields[0].title);
      this.points.push(point);
    });
  }

  redirectToAvistamiento(id: number){

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

  calculatePoint(lat: number, long: number, status:string, id:number, isVisible:boolean, description: string, title: string): 
    { x: number, y: number, status:string, id:number, isVisible:boolean, description: string, title: string } {
    const width = 1305; // Ancho de la imagen en píxeles
    const height = 500; // Alto de la imagen en píxeles

    const x = ((long - this.longTopLeft) / (this.longTopRight - this.longTopLeft)) * width;
    const y = ((lat - this.latTopLeft) / (this.latBottomLeft - this.latTopLeft)) * height;

    return { x, y, status, id , isVisible, description, title};
  }

  calculateLatitude(y: number, height: number): number {
    const latRange = this.latBottomLeft - this.latTopLeft;
    return this.latTopLeft + (y / height) * latRange;
  }

  calculateLongitude(x: number, width: number): number {
    const longRange = this.longTopRight - this.longTopLeft;
    return this.longTopLeft + (x / width) * longRange;
  }

  showLocationInfo(id: number) {
    const point = this.points.find(p => p.id === id);
    if (point) {
        point.isVisible = true;
    }
  }

  hideLocationInfo(id: number) {
    const point = this.points.find(p => p.id === id);
    if (point) {
        point.isVisible = false;
    }
  }
}
