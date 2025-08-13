import { Component } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AvistamientoService } from '../../services/avistamiento.service';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponentComponent } from '../dialog-component/dialog-component.component';
import { ImageModalComponent } from '../image-modal/image-modal.component';
import { lastValueFrom } from 'rxjs/internal/lastValueFrom';

@Component({
  selector: 'app-avistamiento-detalle',
  templateUrl: './avistamiento-detalle.component.html',
  styleUrls: ['./avistamiento-detalle.component.css']
})
export class AvistamientoDetalleComponent {
  id: string | null = null;
  avistamiento: any = null;
  isLoading: boolean = true;

  constructor(
    private avistamientoService: AvistamientoService,
    private dialog: MatDialog,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void> {
    try {
      this.isLoading = true;
      this.activatedRoute.params.subscribe(async (params: Params) => {
        if (params && params['id']) {
          this.id = params['id'];
          this.avistamiento = await this.avistamientoService.getAvistamientoById(this.id);
          console.log('Avistamiento loaded:', this.avistamiento);
          this.isLoading = false;
        } else {
          console.error('No ID found in route parameters');
          this.isLoading = false;
          this.router.navigate(['/home']);
        }
      });
    } catch (error) {
      console.error('Error loading avistamiento:', error);
      this.isLoading = false;
      this.router.navigate(['/home']);
    }
  }

  async viewImages() {
  if (!this.avistamiento?.id) {
    alert('No se ha cargado información del avistamiento');
    return;
  }

  try {
    const imageUrls = await lastValueFrom(
      this.avistamientoService.getImageUrlsBySighting(this.avistamiento.id)
    );

    if (!imageUrls || imageUrls.length === 0) {
      throw new Error('No hay imágenes disponibles para este avistamiento');
    }

    this.dialog.open(ImageModalComponent, {
      width: '80vw',
      maxWidth: '1200px',
      panelClass: 'custom-image-modal',
      data: { 
        images: imageUrls.map(url => ({ url })),
        sightingId: this.avistamiento.id
      }
    });
  } catch (error: any) {
    console.error('Error al cargar imágenes:', error);
    alert('Error: ' + error.message);
  }
}
}