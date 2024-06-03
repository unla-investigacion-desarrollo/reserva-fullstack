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
 rtaLogin: boolean = true;
constructor(private router2: Router, private loginService: LoginService) {
    this.router = router2;
  }

async login(usernameOrEmail, password){
  try{
    this.rtaLogin = await this.loginService.login(usernameOrEmail, password);
  
    if(this.rtaLogin){
      this.router.navigate(['/', 'home'])
    }else{
      this.rtaLogin = false;
    }
  }catch(ex){
    this.rtaLogin = false;
  }
}
}
