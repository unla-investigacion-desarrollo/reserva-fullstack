import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { lastValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private headers = new HttpHeaders({
    'Content-Type': 'application/json'
  });

  private rta: any;

  constructor(private http: HttpClient) { }

  async login(usernameOrEmail, password){

    const url = 'http://localhost:8000/account/login';
    const body = {
      // usernameOrEmail: "nfiasche",
      // password: "nfiasche"
      usernameOrEmail: usernameOrEmail,
      password: password
    };

    this.rta = await lastValueFrom(this.http.post(url, body, { headers: this.headers }));

    localStorage.setItem('userData', JSON.stringify(this.rta));
    return this.rta.success;
  }
}
