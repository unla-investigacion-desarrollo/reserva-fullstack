import { Component } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AvistamientoService } from '../../services/avistamiento.service';
import {MatDialog} from '@angular/material/dialog'; 
import { DialogComponentComponent } from '../../components/dialog-component/dialog-component.component';

@Component({
  selector: 'app-edit-avistamiento',
  templateUrl: './edit-avistamiento.component.html',
  styleUrls: ['./edit-avistamiento.component.css']
})
export class EditAvistamientoComponent {
  private id: string;
  avistamiento: any;
  
  constructor(private avistamientoService: AvistamientoService, 
    private dialog: MatDialog,
    private activatedRoute: ActivatedRoute) { }

  async ngOnInit(): Promise<void> {

    this.activatedRoute.params.subscribe((params: Params) => {
      if(params != null && params.id != null){
        this.id = params["id"];
      }
    });
    this.avistamiento = await this.avistamientoService.getAvistamientoById(this.id);
  }

  async Aprobar(){

    await this.avistamientoService.updateStatusAvistamiento(JSON.parse(localStorage.getItem("userData")).data.id, Number(this.id), "aprobado");

    this.dialog.open(DialogComponentComponent, {
      data: { type:0, dialog: this.dialog},
    });
  }

  async Rechazar(){

    await this.avistamientoService.updateStatusAvistamiento(JSON.parse(localStorage.getItem("userData")).data.id, Number(this.id), "reprobado");

    this.dialog.open(DialogComponentComponent, {
      data: { type:1, dialog: this.dialog},
    });
  }
}
