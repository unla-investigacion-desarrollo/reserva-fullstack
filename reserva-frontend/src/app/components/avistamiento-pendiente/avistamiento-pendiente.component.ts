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
  avistamientos: any[] = [];
  isLoading: boolean = true;
  searchTerm: string = '';
  dateFilter: string = 'all';
  
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
    this.avistamientoService.getAvistamientosPendientes()
      .then((data) => {
        this.avistamientos = data;
        this.isLoading = false;
      })
      .catch((error) => {
        console.error('Error loading sightings:', error);
        this.isLoading = false;
      });
  }

  async viewImage(sightingId: number) {
    try {
      const imageUrls = await lastValueFrom(
        this.avistamientoService.getImageUrlsBySighting(sightingId)
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
          sightingId: sightingId
        }
      });
    } catch (error: any) {
      console.error('Error al cargar imágenes:', error);
      alert(error.message);
    }
  }

  get filteredAvistamientos(): any[] {
    return this.avistamientos.filter(avist => {
      const matchesSearch = avist.scientificName.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
        avist.createdBy.username.toLowerCase().includes(this.searchTerm.toLowerCase());

      const matchesDate = this.filterByDate(avist.createdAt);

      return matchesSearch && matchesDate;
    });
  }

  private filterByDate(date: string): boolean {
    const sightingDate = new Date(date);
    const today = new Date();

    switch (this.dateFilter) {
      case 'today':
        return sightingDate.toDateString() === today.toDateString();
      case 'week':
        const oneWeekAgo = new Date();
        oneWeekAgo.setDate(today.getDate() - 7);
        return sightingDate >= oneWeekAgo;
      case 'month':
        const oneMonthAgo = new Date();
        oneMonthAgo.setMonth(today.getMonth() - 1);
        return sightingDate >= oneMonthAgo;
      default:
        return true;
    }
  }
}