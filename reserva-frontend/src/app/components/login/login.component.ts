import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

private router: Router;

constructor(private router2: Router, private http: HttpClient) {
    this.router = router2;
  }

async login(){

  const url = 'http://localhost:8000/account/login';
    const body = {
      usernameOrEmail: "nfiasche",
      password: "nfiasche"
    };
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    var rta = await this.http.post(url, body, { headers: headers }).toPromise();
    console.log(rta);
  // this.router.navigate(['/', 'home']);
}

}
