import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { ExpertoGuard } from './experto.guard';

describe('expertoGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => ExpertoGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
