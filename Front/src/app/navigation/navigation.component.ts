import { Component, OnInit } from '@angular/core';
import { BreakpointObserver, Breakpoints, MediaMatcher } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { AuthService } from '../gallery/services/auth/auth.service';
import { Router } from '@angular/router';
import { AuthGuard } from '../gallery/services/guards/auth.guard';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {


  isHandset$: Observable<boolean> = this.breakpointObserver.observe('(max-width: 900px)')
    .pipe(
      tap((x) => console.log(x)),
      map(result => result.matches)
    );

  constructor(private breakpointObserver: BreakpointObserver,
              public authService: AuthService,
              private router: Router,
              private mediaMatcher: MediaMatcher,
              private authGuard: AuthGuard,
              private httpClient: HttpClient) {
  }

  ngOnInit() {
    console.log(this.authService);
    if (this.authService.token && this.authService.token !== 'null') {
      console.log('xD');
      this.httpClient.get(`https://localhost:8443/user/get`)
        .subscribe((req) => {
          this.authService.user = req;
        });
    }
  }


  isAuthenticated() {
    return this.authService.user != null;
  }

  logout() {
    this.authService.user = null;
    this.authService.token = null;
    this.router.navigateByUrl('/dogs');
  }
}
