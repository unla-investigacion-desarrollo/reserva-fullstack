import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../../services/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {

  constructor(private router: Router, private loginService: LoginService) {}

  logout(){
    this.loginService.isLoggedIn = false;
    localStorage.removeItem('userData');
    this.router.navigate(['/login']);
  }

}
