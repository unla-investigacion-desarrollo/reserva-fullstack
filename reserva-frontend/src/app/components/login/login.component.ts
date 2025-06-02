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
  loading: boolean = false;
  usernameOrEmail: string = '';
  userPassword: string = '';
  rememberMe: boolean = false;

  constructor(private router: Router, private loginService: LoginService) {}

  async login() {
    if (!this.usernameOrEmail?.trim() || !this.userPassword?.trim()) {
      this.rtaLogin = false;
      return;
    }
    this.loading = true;
    try {
      this.rtaLogin = await this.loginService.login(
        this.usernameOrEmail.trim(), 
        this.userPassword.trim()
      );
      if (this.rtaLogin) {
        this.router.navigate(['/home']);
      }
    } catch (ex) {
      this.rtaLogin = false;
      console.error('Login error:', ex);
    } finally {
      this.loading = false;
    }
  }

  // Asegura que el navbar se oculte
  shouldHideNavbar(): boolean {
    return this.router.url.includes('/login');
  }
}