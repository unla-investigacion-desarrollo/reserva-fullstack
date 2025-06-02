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
  images?: { id: number; url: string }[];
}

@Component({
  selector: 'app-avistamiento-aprobado',
  templateUrl: './avistamiento-aprobado.component.html',
  styleUrls: ['./avistamiento-aprobado.component.css'],
  providers: [DatePipe],
})
export class AvistamientoAprobadoComponent implements OnInit {
  avistamientos: Avistamiento[] = [];
  pagedAvistamientos: Avistamiento[] = [];
  isLoading: boolean = true;
  errorMessage: string | null = null;
  currentPage: number = 1;
  itemsPerPage: number = 10;
  totalPages: number = 1;

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
      this.avistamientos = await this.avistamientoService.getAvistamientosAprobados();
      this.updatePagedAvistamientos();
    } catch (error) {
      console.error('Error al cargar avistamientos aprobados:', error);
      this.errorMessage = 'No se pudieron cargar los avistamientos aprobados. Inténtelo de nuevo más tarde.';
    } finally {
      this.isLoading = false;
    }
  }

  updatePagedAvistamientos(): void {
    this.totalPages = Math.ceil(this.avistamientos.length / this.itemsPerPage);
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.pagedAvistamientos = this.avistamientos.slice(startIndex, endIndex);
  }

  getPages(): number[] {
    const pages = [];
    for (let i = 1; i <= this.totalPages; i++) {
      pages.push(i);
    }
    return pages;
  }

  goToPage(page: number, event: Event): void {
    event.preventDefault();
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updatePagedAvistamientos();
    }
  }

  previousPage(event: Event): void {
    event.preventDefault();
    if (this.currentPage > 1) {
      this.currentPage--;
      this.updatePagedAvistamientos();
    }
  }

  nextPage(event: Event): void {
    event.preventDefault();
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.updatePagedAvistamientos();
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
        width: '40vw',
        maxWidth: 'none',
        panelClass: 'custom-image-modal',
        data: { images: urls.map(url => ({ url })) }
      });
    } catch (error: any) {
      alert(error.message);
    }
  }

  async deleteAvistamiento(id: number): Promise<void> {
    if (confirm('¿Estás seguro de que deseas eliminar este avistamiento?')) {
      try {
        await this.avistamientoService.deleteAvistamiento(id);
        this.avistamientos = this.avistamientos.filter(avist => avist.id !== id);
        this.updatePagedAvistamientos();
      } catch (error) {
        console.error('Error al eliminar avistamiento:', error);
        alert('No se pudo eliminar el avistamiento. Inténtelo de nuevo más tarde.');
      }
    }
  }
}