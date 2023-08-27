import { Component } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AvistamientoService } from '../../services/avistamiento.service';
import { DialogService } from 'src/app/services/dialog-service.service';
import {MatDialog, MatDialogConfig} from '@angular/material/dialog'; 
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
    private _dialog: DialogService,
    private dialog: MatDialog,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {

    this.activatedRoute.params.subscribe((params: Params) => {
      if(params != null && params.id != null){
        this.id = params["id"];
      }
    });

    this.avistamiento = JSON.parse(this.avistamientoService.getAvistamientoById(this.id));
  }

  Aprobar(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.position = {
      'top': '0',
      left: '0'
  };
    this.dialog.open(DialogComponentComponent, dialogConfig);
  }

  Rechazar(){
    const dialogConfig = new MatDialogConfig();
    dialogConfig.position = {
      'top': '0',
      left: '0'
    };
    this.dialog.open(DialogComponentComponent, dialogConfig);
  }
}
