import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dialog-component',
  templateUrl: './dialog-component.component.html',
  styleUrls: ['./dialog-component.component.css']
})
export class DialogComponentComponent {
  dialogType: number;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    private dialogRef: MatDialogRef<DialogComponentComponent>,
    private router: Router
  ) {
    this.dialogType = data.type;
  }

  closeDialog(type: number) {
    this.dialogRef.close(); // ✅ cerrar el modal correctamente
    if (type === 0) {
      this.router.navigate(['/avistamientos_pendientes']);
    } else {
      this.router.navigate(['/avistamientos_aprobados']);
    }
  }
}
