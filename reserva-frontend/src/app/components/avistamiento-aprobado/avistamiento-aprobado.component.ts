import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';

@Component({
  selector: 'app-avistamiento-aprobado',
  templateUrl: './avistamiento-aprobado.component.html',
  styleUrls: ['./avistamiento-aprobado.component.css']
})
export class AvistamientoAprobadoComponent {

  avistamientos: any;

  constructor(private avistamientoService: AvistamientoService) {}

  async ngOnInit(): Promise<void> {
    // this.avistamientos = JSON.parse(this.avistamientoService.getAvistamientosAprobados());

    this.avistamientos = await this.avistamientoService.getAvistamientosAprobados();
    this.avistamientos = this.avistamientos.data;
  }
}
