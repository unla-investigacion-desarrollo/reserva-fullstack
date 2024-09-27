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
  avistamientos: any;

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
    this.avistamientos = await this.avistamientoService.getCategorias();
    console.log(this.avistamientos.name);
    
    /*this.avistamientos = [
      { name: 'Uno' },
      { name: 'Dos' },
      { name: 'Tres' },
      { name: 'Cuatro'}
    ];*/
  }

  /*async updateAvistamiento(avistamientoId){
    this.avistamiento = await this.avistamientoService.getAvistamientoById(this.id);
    await this.avistamientoService.updateAvistamiento( Number(this.id), JSON.parse(localStorage.getItem("userData")).data.id, this.avistamiento.name, this.avistamiento.scientificName, 
    //this.avistamiento.latitude, this.avistamiento.longitude,
    this.avistamiento.fields[0].descripcion , Number(this.avistamiento.type.id));
    
    this.dialog.open(DialogComponentComponent, {
      data: { type:0, dialog: this.dialog},
    });
  }*/

  async updateAvistamiento(avistamientoId, scientificName, name, description, typeId){
    //avistamiento.id, avistamiento.scientificName, avistamiento.name, avistamiento.fields[0].description, avistamiento.type.id
    this.avistamiento = await this.avistamientoService.getAvistamientoById(this.id);      
    await this.avistamientoService.updateAvistamiento( Number(avistamientoId), JSON.parse(localStorage.getItem("userData")).data.id, name, scientificName, description, Number(typeId));
    //this.avistamiento.latitude, this.avistamiento.longitude
      
    this.dialog.open(DialogComponentComponent, {
      data: { type:0, dialog: this.dialog},
    });
  }
}