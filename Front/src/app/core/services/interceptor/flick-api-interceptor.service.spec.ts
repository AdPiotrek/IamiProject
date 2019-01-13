import { TestBed } from '@angular/core/testing';

import { FlickApiInterceptorService } from './flick-api-interceptor.service';

describe('FlickApiInterceptorService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: FlickApiInterceptorService = TestBed.get(FlickApiInterceptorService);
    expect(service).toBeTruthy();
  });
});
