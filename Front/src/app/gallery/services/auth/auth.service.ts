import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  user: any;

  set token(token: string) {
    localStorage.setItem('Authorization', token);
  }

  get token() {
    return localStorage.getItem('Authorization');
  }

  constructor() { }
}
