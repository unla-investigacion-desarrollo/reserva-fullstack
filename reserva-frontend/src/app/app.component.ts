import { Component } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'reserva-frontend';

  showHead: boolean = false; // Controla la visibilidad del navbar

  constructor(private router: Router) {
    // Escuchamos los eventos de navegación
    router.events.forEach((event) => {
      if (event instanceof NavigationStart) {
        // Oculta el navbar en las rutas de login y "olvidaste tu contraseña"
        this.showHead = !(event.url === '/login' || event.url === '/forgot-password' || event.url === '/register');
      }
    });
  }
}
