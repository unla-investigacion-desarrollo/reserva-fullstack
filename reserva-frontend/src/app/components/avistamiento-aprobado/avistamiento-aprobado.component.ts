import { Component, OnInit } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { DatePipe } from '@angular/common';
import { lastValueFrom } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ImageModalComponent } from '../image-modal/image-modal.component';

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
  selector: 'app-avistamiento-aprobado',
  templateUrl: './avistamiento-aprobado.component.html',
  styleUrls: ['./avistamiento-aprobado.component.css'],
  providers: [DatePipe], // Proveer DatePipe para usarlo en la plantilla
})
export class AvistamientoAprobadoComponent implements OnInit {
  avistamientos: Avistamiento[] = []; // Inicializar como un arreglo vacío
  isLoading: boolean = true;
  errorMessage: string | null = null;

  constructor(
    private avistamientoService: AvistamientoService,
    private datePipe: DatePipe,
    private dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.cargarAvistamientosAprobados();
  }

  async cargarAvistamientosAprobados(): Promise<void> {
    this.isLoading = true;
    this.errorMessage = null;

    try {
      // La respuesta ahora es directamente el array de avistamientos aprobados
      this.avistamientos = await this.avistamientoService.getAvistamientosAprobados();
    } catch (error) {
      console.error('Error al cargar avistamientos aprobados:', error);
      this.errorMessage = 'No se pudieron cargar los avistamientos aprobados. Inténtelo de nuevo más tarde.';
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
