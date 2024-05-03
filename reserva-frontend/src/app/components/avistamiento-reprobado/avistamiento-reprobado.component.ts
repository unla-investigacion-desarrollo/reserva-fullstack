import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';

@Component({
  selector: 'app-avistamiento-reprobado',
  templateUrl: './avistamiento-reprobado.component.html',
  styleUrls: ['./avistamiento-reprobado.component.css']
})
export class AvistamientoReprobadoComponent {
  avistamientos: any;

  constructor(private avistamientoService: AvistamientoService) {}

  async ngOnInit(): Promise<void> {

    this.avistamientos = await this.avistamientoService.getAvistamientosReprobados();
    this.avistamientos = this.avistamientos.data;
  }
}
