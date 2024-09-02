import { Component } from '@angular/core';

@Component({
  selector: 'app-map-component',
  templateUrl: './map-component.component.html',
  styleUrls: ['./map-component.component.css']
})
export class MapComponentComponent {
 // Coordenadas de las esquinas de la imagen
 latTopLeft = -34.778991;
 longTopLeft = -58.437050;
 
 latTopRight = -34.783689;
 longTopRight = -58.433513;
 
 latBottomLeft = -34.779984;
 longBottomLeft = -58.438908;
 
 latBottomRight = -34.784489;
 longBottomRight = -58.435327;

 point: { x: number, y: number } | null = null;

 onMapClick(event: MouseEvent) {
    const mapElement = event.target as HTMLElement;
    const rect = mapElement.getBoundingClientRect();
    const x = event.clientX - rect.left;
    const y = event.clientY - rect.top;

    this.point = { x, y };
  }
}
