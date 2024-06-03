import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-avistamiento-pendiente',
  templateUrl: './avistamiento-pendiente.component.html',
  styleUrls: ['./avistamiento-pendiente.component.css']
})

export class AvistamientoPendienteComponent {

  avistamientos: any;

  constructor(private avistamientoService: AvistamientoService, private datePipe: DatePipe) {}

  async ngOnInit(): Promise<void> {
    this.avistamientos = await this.avistamientoService.getAvistamientosPendientes();
    this.avistamientos = this.avistamientos.data;
  }

  async deleteAvistamiento(avistamientoId){
    await this.avistamientoService.deleteAvistamiento(avistamientoId);
    location.reload();
  }
}
