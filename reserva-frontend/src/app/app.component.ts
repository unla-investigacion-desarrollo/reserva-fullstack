import { Component } from '@angular/core';
import { Router, NavigationStart } from '@angular/router';
import { LoginService } from './services/login.service';

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

  constructor(private router: Router, private loginService: LoginService) {
    router.events.forEach((event) => {
      if (event instanceof NavigationStart) {
        if (loginService.checkLogin()) {
          this.showHead = true;
        } else {
          this.showHead = false;
        }
      }
    });
  }
}
