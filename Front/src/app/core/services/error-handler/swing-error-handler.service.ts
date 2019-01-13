import { ErrorHandler, Inject, Injector, OnDestroy } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';

import { empty, of, interval, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';

import { ErrorLog } from './error-log';
import { environment } from '../../../../environments/environment';

export class SwingErrorHandlerService implements ErrorHandler, OnDestroy {
  private http: HttpClient;
  private stackTrace: ErrorLog[] = [];
  private loggerSubscription: Subscription;

  constructor(@Inject(Injector) private injector: Injector) {
    this.http = injector.get(HttpClient);
    this.sendLogs();
  }

  handleError(error: Error | HttpErrorResponse): void {
    if (!environment.production) {
      console.error(error);
    }
    this.stackTrace.push({
      date: new Date(),
      error: error.message
    });
  }

  sendLogs() {
    this.loggerSubscription = interval(60000)
      .pipe(
        switchMap(() => {
          console.log('stackTrace', this.stackTrace);
          if (this.stackTrace.length > 0) {
            // return this.http.post('Some example url to send logs to server', this.stackTrace);
            return of('Saved to file');
          }
          return empty();
        })
      )
      .subscribe(
        () => {
          this.stackTrace = [];
        });
  }

  ngOnDestroy() {
    this.loggerSubscription.unsubscribe();
  }



}
