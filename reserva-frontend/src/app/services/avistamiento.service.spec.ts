import { TestBed } from '@angular/core/testing';

import { AvistamientoService } from './avistamiento.service';

describe('AvistamientoService', () => {
  let service: AvistamientoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AvistamientoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
