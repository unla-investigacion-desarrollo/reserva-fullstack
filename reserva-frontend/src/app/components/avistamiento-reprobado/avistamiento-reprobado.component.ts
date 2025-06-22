import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router'; // Importar Router
import { lastValueFrom } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ImageModalComponent } from '../image-modal/image-modal.component';

@Component({
  selector: 'app-avistamiento-reprobado',
  templateUrl: './avistamiento-reprobado.component.html',
  styleUrls: ['./avistamiento-reprobado.component.css']
})
export class AvistamientoReprobadoComponent {
  avistamientos: any = [];
  filteredAvistamientos: any[] = [];
  isLoading: boolean = true;
  searchTerm: string = '';
  reasonFilter: string = 'all';
  selectedAvistamiento: any = null;


  constructor(
    private avistamientoService: AvistamientoService,
    private datePipe: DatePipe,
    private router: Router, // Inyectar Router
    private dialog: MatDialog // Inyectar MatDialog
  ) {}

  ngOnInit(): void {
    this.loadAvistamientos();
  }

  async loadAvistamientos(): Promise<void> {
    this.isLoading = true;
    try {
      const data = await this.avistamientoService.getAvistamientosReprobados();
      this.avistamientos = data;
      this.filteredAvistamientos = [...data];
      this.isLoading = false;
    } catch (error) {
      console.error('Error loading rejected sightings:', error);
      this.isLoading = false;
    }
  }

  goBack(): void {
    this.router.navigate(['/home']); // Ruta al menú principal
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

  applyFilters(): void {
    this.filteredAvistamientos = this.avistamientos.filter(avist => {
      const matchesSearch = avist.scientificName.toLowerCase().includes(this.searchTerm.toLowerCase()) || 
                          avist.createdBy.username.toLowerCase().includes(this.searchTerm.toLowerCase());
      
      const matchesReason = this.reasonFilter === 'all' || 
                          (avist.rejectionReason && avist.rejectionReason.toLowerCase().includes(this.reasonFilter));
      
      return matchesSearch && matchesReason;
    });
  }

}
