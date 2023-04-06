import { TestBed } from '@angular/core/testing';

import { City2dataService } from './city2data.service';

describe('City2dataService', () => {
  let service: City2dataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(City2dataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
