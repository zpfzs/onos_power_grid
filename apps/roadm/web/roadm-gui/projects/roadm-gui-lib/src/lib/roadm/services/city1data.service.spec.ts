import { TestBed } from '@angular/core/testing';

import { City1dataService } from './city1data.service';

describe('City1dataService', () => {
  let service: City1dataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(City1dataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
