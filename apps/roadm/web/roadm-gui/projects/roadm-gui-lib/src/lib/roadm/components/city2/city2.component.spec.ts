import { ComponentFixture, TestBed } from '@angular/core/testing';

import { City2Component } from './city2.component';

describe('City2Component', () => {
  let component: City2Component;
  let fixture: ComponentFixture<City2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ City2Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(City2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
