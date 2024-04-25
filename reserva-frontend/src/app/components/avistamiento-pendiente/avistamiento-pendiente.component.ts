import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { Pipe, PipeTransform } from '@angular/core';

@Component({
  selector: 'app-avistamiento-pendiente',
  templateUrl: './avistamiento-pendiente.component.html',
  styleUrls: ['./avistamiento-pendiente.component.css']
})

export class AvistamientoPendienteComponent {

  avistamientos: any;

  constructor(private avistamientoService: AvistamientoService) {}

  async ngOnInit(): Promise<void> {
    this.avistamientos = await this.avistamientoService.getAvistamientosPendientes();
    this.avistamientos = this.avistamientos.data;
  }
}
