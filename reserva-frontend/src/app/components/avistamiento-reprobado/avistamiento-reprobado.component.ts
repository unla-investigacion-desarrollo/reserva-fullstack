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
  isLoading: boolean = false;

  constructor(
    private avistamientoService: AvistamientoService,
    private datePipe: DatePipe,
    private router: Router, // Inyectar Router
    private dialog: MatDialog // Inyectar MatDialog
  ) {}

  async ngOnInit(): Promise<void> {
    this.isLoading = true;

    try {
      const response = await this.avistamientoService.getAvistamientosReprobados();
      this.avistamientos = response;
    } catch (error) {
      console.error('Error cargando avistamientos reprobados:', error);
    } finally {
      this.isLoading = false;
    }
  }

  goBack(): void {
    this.router.navigate(['/home']); // Ruta al menú principal
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
