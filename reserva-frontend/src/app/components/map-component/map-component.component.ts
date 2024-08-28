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

 onMapClick(event: MouseEvent) {
   const mapContainer = event.target as HTMLElement;
   const rect = mapContainer.getBoundingClientRect();

   // Cálculo de las coordenadas relativas al clic
   const clickX = event.clientX - rect.left;
   const clickY = event.clientY - rect.top;

   // Proporciones en X e Y
   const xRatio = clickX / rect.width;
   const yRatio = clickY / rect.height;

   // Interpolación de latitud y longitud según la posición
   const latTop = this.latTopLeft + (this.latTopRight - this.latTopLeft) * xRatio;
   const latBottom = this.latBottomLeft + (this.latBottomRight - this.latBottomLeft) * xRatio;
   const lat = latTop + (latBottom - latTop) * yRatio;

   const longTop = this.longTopLeft + (this.longTopRight - this.longTopLeft) * xRatio;
   const longBottom = this.longBottomLeft + (this.longBottomRight - this.longBottomLeft) * xRatio;
   const long = longTop + (longBottom - longTop) * yRatio;

   console.log(`Latitud: ${lat}, Longitud: ${long}`);
 }
}
