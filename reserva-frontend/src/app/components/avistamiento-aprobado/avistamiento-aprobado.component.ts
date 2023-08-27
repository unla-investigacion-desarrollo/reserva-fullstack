import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';

@Component({
  selector: 'app-avistamiento-aprobado',
  templateUrl: './avistamiento-aprobado.component.html',
  styleUrls: ['./avistamiento-aprobado.component.css']
})
export class AvistamientoAprobadoComponent {

  avistamientos: string;

  constructor(private avistamientoService: AvistamientoService) {}

  ngOnInit(): void {
    this.avistamientos = JSON.parse(this.avistamientoService.getAvistamientosAprobados());
  }
}
