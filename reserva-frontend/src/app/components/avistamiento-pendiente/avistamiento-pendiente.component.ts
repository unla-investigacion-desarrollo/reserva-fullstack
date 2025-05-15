import { Component, OnInit } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { DatePipe } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { ImageModalComponent } from '../image-modal/image-modal.component';
import { lastValueFrom } from 'rxjs';

interface Avistamiento {
  id: number;
  scientificName: string;
  createdBy: {
    username: string;
  };
  latitude: number;
  longitude: number;
  createdAt: string;
  images?: {id: number, url: string}[]; // Añadimos la propiedad de imágenes
}

@Component({
  selector: 'app-avistamiento-pendiente',
  templateUrl: './avistamiento-pendiente.component.html',
  styleUrls: ['./avistamiento-pendiente.component.css'],
  providers: [DatePipe],
})
export class AvistamientoPendienteComponent implements OnInit {
  avistamientos: Avistamiento[] = [];
  isLoading: boolean = true;
  errorMessage: string | null = null;

  constructor(
    private avistamientoService: AvistamientoService,
    private datePipe: DatePipe,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.cargarAvistamientos();
  }

  async cargarAvistamientos(): Promise<void> {
    this.isLoading = true;
    this.errorMessage = null;

    try {
      this.avistamientos = await this.avistamientoService.getAvistamientosPendientes();
    } catch (error) {
      console.error('Error al cargar avistamientos:', error);
      this.errorMessage = 'No se pudieron cargar los avistamientos. Inténtelo de nuevo más tarde.';
    } finally {
      this.isLoading = false;
    }
  }

  async viewImage(sightingId: number) {
    try {
      const urls = await lastValueFrom(
        this.avistamientoService.getImageUrlsBySighting(sightingId)
      );

      if (!urls || urls.length === 0) {
        throw new Error('No hay imágenes disponibles para este avistamiento');
      }

      this.dialog.open(ImageModalComponent, {
        width: '40vw', // o más si querés
        maxWidth: 'none', // quita el límite de 80% o 600px
        panelClass: 'custom-image-modal',
        data: { images: urls.map(url => ({ url })) }
      });

    } catch (error) {
      alert(error.message);
    }
  }
}