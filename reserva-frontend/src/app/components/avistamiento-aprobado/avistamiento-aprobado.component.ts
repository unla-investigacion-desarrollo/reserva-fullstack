import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-avistamiento-aprobado',
  templateUrl: './avistamiento-aprobado.component.html',
  styleUrls: ['./avistamiento-aprobado.component.css']
})
export class AvistamientoAprobadoComponent {

  avistamientos: any;

  constructor(private avistamientoService: AvistamientoService, private datePipe: DatePipe) {}

  async ngOnInit(): Promise<void> {
    this.avistamientos = await this.avistamientoService.getAvistamientosAprobados();
    this.avistamientos = this.avistamientos.data;
  }
}
