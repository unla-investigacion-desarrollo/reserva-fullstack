import { Component, OnInit } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { DatePipe } from '@angular/common';

interface Avistamiento {
  id: number;
  scientificName: string;
  createdBy: {
    username: string;
  };
  latitude: number;
  longitude: number;
  createdAt: string;
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
    private datePipe: DatePipe
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
}
