import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

private router: Router;

constructor(private router2: Router, private loginService: LoginService) {
    this.router = router2;
  }

async login(usernameOrEmail, password){
  await this.loginService.login(usernameOrEmail, password) ? this.router.navigate(['/', 'home']) : null;
}

}
