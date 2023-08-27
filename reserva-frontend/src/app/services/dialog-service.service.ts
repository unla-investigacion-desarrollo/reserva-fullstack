import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
//component we just created

@Injectable()

export class DialogService {

  constructor(private dialog: MatDialog) {}

  open(data: any) {


  }
}