import { Component } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'reserva-frontend';

  showHead: boolean = false;

  ngOnInit() {
  }

  constructor(private router: Router) {
    router.events.forEach((event) => {
      if (event instanceof NavigationStart) {
        if (event['url'] != '/login') {
          this.showHead = true;
        } else {
          this.showHead = false;
        }
      }
    });
  }
}
