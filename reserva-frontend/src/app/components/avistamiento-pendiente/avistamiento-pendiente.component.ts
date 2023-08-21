import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';
import { Pipe, PipeTransform } from '@angular/core';

@Component({
  selector: 'app-avistamiento-pendiente',
  templateUrl: './avistamiento-pendiente.component.html',
  styleUrls: ['./avistamiento-pendiente.component.css']
})

export class AvistamientoPendienteComponent {

  avistamientos: string;

  constructor(private avistamientoService: AvistamientoService) {}

  ngOnInit(): void {
    this.avistamientos = JSON.parse(this.avistamientoService.getAvistamientosPendientes());
  }
}
