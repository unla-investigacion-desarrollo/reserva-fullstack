import { Component, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dialog-component',
  templateUrl: './dialog-component.component.html',
  styleUrls: ['./dialog-component.component.css']
})
export class DialogComponentComponent {

  dialogType: number;
  dialog: MatDialog;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private router: Router) {
    this.dialogType = data.type;
    this.dialog = data.dialog;

  }

  closeDialog(type){
    this.dialog.closeAll();
    if(type == 0){
      this.router.navigate(['/avistamientos_pendientes']);
    }else{
      this.router.navigate(['/avistamientos_aprobados']);
    }
  }
}
