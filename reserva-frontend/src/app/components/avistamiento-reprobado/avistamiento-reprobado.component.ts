import { Component } from '@angular/core';
import { AvistamientoService } from '../../services/avistamiento.service';

@Component({
  selector: 'app-avistamiento-reprobado',
  templateUrl: './avistamiento-reprobado.component.html',
  styleUrls: ['./avistamiento-reprobado.component.css']
})
export class AvistamientoReprobadoComponent {
  avistamientos: string;

  constructor(private avistamientoService: AvistamientoService) {}

  ngOnInit(): void {
    this.avistamientos = JSON.parse(this.avistamientoService.getAvistamientosReprobados());
  }
}
