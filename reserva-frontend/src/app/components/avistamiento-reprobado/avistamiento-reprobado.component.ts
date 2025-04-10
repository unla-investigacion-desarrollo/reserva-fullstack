import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { DatePipe } from '@angular/common';
import { Router } from '@angular/router'; // Importar Router

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
    private router: Router // Inyectar Router
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
}
