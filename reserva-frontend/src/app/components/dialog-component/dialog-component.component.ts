import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-dialog-component',
  templateUrl: './dialog-component.component.html',
  styleUrls: ['./dialog-component.component.css']
})
export class DialogComponentComponent {
  dialogType: number;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<DialogComponentComponent>
  ) {
    console.log('Dialog Data:', data); // Debug log to verify data
    this.dialogType = data?.type ?? -1; // Fallback to -1 if type is undefined
    console.log('Dialog Type:', this.dialogType); // Debug log to verify dialogType
  }

  closeDialog(type: number): void {
    this.dialogRef.close(type);
  }
}