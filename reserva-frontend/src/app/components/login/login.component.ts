import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  rtaLogin: boolean = true;

  constructor(private router: Router, private loginService: LoginService) {}

  async login(usernameOrEmail: string, password: string) {
    try {
      this.rtaLogin = await this.loginService.login(usernameOrEmail, password);
      if (this.rtaLogin) {
        this.router.navigate(['/', 'home']);
      } else {
        this.rtaLogin = false;
      }
    } catch (ex) {
      this.rtaLogin = false;
    }
  }

  // Método para verificar si estamos en la vista de login
  isLoginPage(): boolean {
    return this.router.url === '/login' || this.router.url === '/forgot-password';
  }
}