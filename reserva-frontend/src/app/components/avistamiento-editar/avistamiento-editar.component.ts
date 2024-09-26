import { Component } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AvistamientoService } from '../../services/avistamiento.service';
import {MatDialog} from '@angular/material/dialog'; 
import { NgModule } from '@angular/core';
import { DialogComponentComponent } from '../../components/dialog-component/dialog-component.component';

@Component({
  selector: 'app-avistamiento-editar',
  templateUrl: './avistamiento-editar.component.html',
  styleUrls: ['./avistamiento-editar.component.css']
})
export class AvistamientoEditarComponent {
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

  async updateAvistamiento(avistamientoId){
    this.avistamiento = await this.avistamientoService.getAvistamientoById(this.id);
    await this.avistamientoService.updateAvistamiento( Number(this.id), JSON.parse(localStorage.getItem("userData")).data.id, this.avistamiento.name, this.avistamiento.scientificName, this.avistamiento.latitude, this.avistamiento.longitude, "nueva descripcion" )
    
    this.dialog.open(DialogComponentComponent, {
      data: { type:0, dialog: this.dialog},
    });
  }
}