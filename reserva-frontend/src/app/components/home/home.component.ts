import { Component } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  // Estos valores podrían venir de un servicio
  pendingCount: number = 5;
  approvedCount: number = 42;
  rejectedCount: number = 8;
}