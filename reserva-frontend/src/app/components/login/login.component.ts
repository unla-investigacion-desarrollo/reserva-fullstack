import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

private router: Router;

constructor(private router2: Router) {
    this.router = router2;
  }

login(){
  this.router.navigate(['/', 'home']);
}

}
