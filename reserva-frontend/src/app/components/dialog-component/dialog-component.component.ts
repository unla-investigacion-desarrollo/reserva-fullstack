import { Component, Inject } from '@angular/core';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
@Component({
  selector: 'app-dialog-component',
  templateUrl: './dialog-component.component.html',
  styleUrls: ['./dialog-component.component.css']
})
export class DialogComponentComponent {

  dialogType: number;

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
    this.dialogType = data.type;
  }

}
