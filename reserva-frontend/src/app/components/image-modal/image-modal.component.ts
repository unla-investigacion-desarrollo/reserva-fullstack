import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-image-modal',
  templateUrl: './image-modal.component.html',
  styleUrls: ['./image-modal.component.css']
})
export class ImageModalComponent {
  activeImageIndex = 0;

  constructor(@Inject(MAT_DIALOG_DATA) public data: {images: {id: number, url: string}[]}) {}

  get currentImage(): {id: number, url: string} | null {
    return this.data.images.length > 0 ? this.data.images[this.activeImageIndex] : null;
  }

  nextImage(): void {
    this.activeImageIndex = (this.activeImageIndex + 1) % this.data.images.length;
  }

  prevImage(): void {
    this.activeImageIndex = (this.activeImageIndex - 1 + this.data.images.length) % this.data.images.length;
  }

  get hasMultipleImages(): boolean {
    return this.data.images.length > 1;
  }
}