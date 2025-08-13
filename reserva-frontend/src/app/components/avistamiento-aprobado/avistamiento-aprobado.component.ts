import { Component, OnInit } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { DatePipe } from '@angular/common';
import { lastValueFrom } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ImageModalComponent } from '../image-modal/image-modal.component';
import { Router } from '@angular/router';

// Import jsPDF y autoTable
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable'; 

interface Avistamiento {
  id: number;
  scientificName: string;
  createdBy: {
    username: string;
    id: number;
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
  avistamientos: any[] = [];
  pagedAvistamientos: any[] = [];
  isLoading: boolean = true;
  searchTerm: string = '';
  sortField: string = 'createdAt';
  itemsPerPage: number = 10;
  currentPage: number = 1;
  totalPages: number = 1;
  errorMessage: string | null = null;
  selectedAvistamiento: any = null;
  imageModal: any;

  // Estadísticas
  thisWeekCount: number = 0;
  thisMonthCount: number = 0;
  uniqueUsersCount: number = 0;

  constructor(
    private avistamientoService: AvistamientoService,
    public datePipe: DatePipe,
    private router: Router,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.cargarAvistamientosAprobados();
  }

  async cargarAvistamientosAprobados(): Promise<void> {
    this.isLoading = true;
    try {
      const data = await this.avistamientoService.getAvistamientosAprobados();
      this.avistamientos = data;
      this.calculateStats();
      this.applyFilters();
      this.isLoading = false;
    } catch (error) {
      console.error('Error loading approved sightings:', error);
      this.isLoading = false;
    }
  }

  calculateStats(): void {
    const now = new Date();
    const oneWeekAgo = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000);
    const oneMonthAgo = new Date(now.getFullYear(), now.getMonth() - 1, now.getDate());

    this.thisWeekCount = this.avistamientos.filter(avist =>
      new Date(avist.createdAt) >= oneWeekAgo
    ).length;

    this.thisMonthCount = this.avistamientos.filter(avist =>
      new Date(avist.createdAt) >= oneMonthAgo
    ).length;

    const uniqueUsers = new Set(this.avistamientos.map(avist => avist.createdBy.id));
    this.uniqueUsersCount = uniqueUsers.size;
  }

  applyFilters(): void {
    let filtered = this.avistamientos.filter(avist =>
      avist.scientificName.toLowerCase().includes(this.searchTerm.toLowerCase()) ||
      avist.createdBy.username.toLowerCase().includes(this.searchTerm.toLowerCase())
    );

    filtered.sort((a, b) => {
      if (a[this.sortField] < b[this.sortField]) return -1;
      if (a[this.sortField] > b[this.sortField]) return 1;
      return 0;
    });

    if (this.sortField === 'createdAt') {
      filtered.reverse();
    }

    this.totalPages = Math.ceil(filtered.length / this.itemsPerPage);
    this.currentPage = Math.min(this.currentPage, this.totalPages);

    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    this.pagedAvistamientos = filtered.slice(startIndex, startIndex + this.itemsPerPage);
  }

  viewDetails(avistamientoId: number): void {
    this.router.navigate(['/avistamiento', avistamientoId]);
  }

  convertImageToBase64(url: string): Promise<string> {
  return new Promise((resolve, reject) => {
    const img = new Image();
    img.crossOrigin = 'Anonymous';
    img.src = url;

    img.onload = () => {
      const canvas = document.createElement('canvas');
      canvas.width = img.width;
      canvas.height = img.height;
      const ctx = canvas.getContext('2d');
      if (!ctx) return reject('No se pudo crear contexto de canvas');
      ctx.drawImage(img, 0, 0);
      const base64 = canvas.toDataURL('image/jpeg');
      resolve(base64);
    };

    img.onerror = (err) => {
      reject('No se pudo cargar la imagen');
    };
  });
}

  async exportSighting(avistamiento: Avistamiento): Promise<void> {
  const doc = new jsPDF();

  doc.setFontSize(18);
  doc.text('Detalles del Avistamiento', 14, 22);

  const details = [
    ['ID:', avistamiento.id.toString()],
    ['Nombre Científico:', avistamiento.scientificName],
    ['Usuario:', avistamiento.createdBy.username],
    ['Fecha:', this.datePipe.transform(avistamiento.createdAt, 'dd/MM/yyyy HH:mm') || ''],
    ['Latitud:', avistamiento.latitude.toString()],
    ['Longitud:', avistamiento.longitude.toString()],
  ];

  autoTable(doc, {
    startY: 30,
    head: [['Campo', 'Valor']],
    body: details,
    theme: 'grid',
  });

  try {
    const imageUrls = await lastValueFrom(this.avistamientoService.getImageUrlsBySighting(avistamiento.id));

    if (imageUrls && imageUrls.length > 0) {
      const base64 = await this.convertImageToBase64(imageUrls[0]); // Primera imagen
      const y = (doc as any).lastAutoTable.finalY + 10;
      //doc.text('Imagen del avistamiento:', 14, y);
      doc.addImage(base64, 'JPEG', 14, y + 5, 180, 100); // Ajusta tamaño según preferencia
    } else {
      const y = (doc as any).lastAutoTable.finalY + 10;
      doc.text('No hay imagen disponible', 14, y);
    }

    doc.save(`avistamiento_${avistamiento.id}.pdf`);
  } catch (error) {
    console.error('Error al obtener la imagen para exportar:', error);
    alert('Error al obtener la imagen. El PDF se generará sin ella.');
    doc.save(`avistamiento_${avistamiento.id}.pdf`);
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
