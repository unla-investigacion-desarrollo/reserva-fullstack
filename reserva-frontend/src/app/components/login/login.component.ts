import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../../services/login.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

private router: Router;
 rtaLogin: boolean = true;
 showLoginForm = true;


constructor(private router2: Router, private loginService: LoginService) {
    this.router = router2;
  }

  toggleForm() {
    this.showLoginForm = !this.showLoginForm;
  }

async login(form: NgForm){
  try{
    if (form.valid) {
      const { usernameOrEmail, password } = form.value;
      this.rtaLogin = await this.loginService.login(usernameOrEmail, password);
  
    if(this.rtaLogin){
      this.router.navigate(['/', 'home'])
    }else{
      this.rtaLogin = false;
    }
    }
    
  }catch(ex){
    this.rtaLogin = false;
  }
}

async register(name, username, email, password){
  try{
    this.rtaLogin = await this.loginService.register(name, username, email, password);
  
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
