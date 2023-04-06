import { ComponentFixture, TestBed } from '@angular/core/testing';

import { City1Component } from './city1.component';

describe('City1Component', () => {
  let component: City1Component;
  let fixture: ComponentFixture<City1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ City1Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(City1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
