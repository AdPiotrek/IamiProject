import { Inject, Injectable, Injector } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../../../gallery/services/auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {
  toastService: ToastrService;
  authService: AuthService;

  constructor(@Inject(Injector) private injector: Injector) {
    this.toastService = injector.get(ToastrService);
    this.authService = injector.get(AuthService);
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.authService.token) {
      if (req.url.includes('localhost')) {
        const reqWithToken = req.clone({
          setHeaders: {
            'Authorization': this.authService.token
          }
        });
        return next.handle(reqWithToken).pipe(
          catchError((err) => {
            this.toastService.error('Żadanie nie powiodło się, jesli nie ma dalszych instrukcji skontaktuj sie z administratorem');
            return throwError(err);
          })
        );
      }

      return next.handle(req);

    } else {
      return next.handle(req);
    }
  }
}
