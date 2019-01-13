import { TestBed } from '@angular/core/testing';

import { SwingErrorHandlerService } from './swing-error-handler.service';

describe('SwingErrorHandlerService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SwingErrorHandlerService = TestBed.get(SwingErrorHandlerService);
    expect(service).toBeTruthy();
  });
});
