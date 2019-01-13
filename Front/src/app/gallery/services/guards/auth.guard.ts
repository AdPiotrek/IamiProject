import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../../services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService,
              private router: Router) {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.authService.token) {
      return true;
    } else if (this.authService.token) {
      this.saveTokenFromSession();
      return true;
    } else {
      this.router.navigate(['']);
      return false;
    }
  }

  saveTokenFromSession() {
    const token: string = sessionStorage.getItem('authToken');
    this.saveToken(token);
  }

  saveToken(token: string): void {
    this.authService.token = token;
  }
}