import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SightingsOverTimeComponent } from './sightings-over-time.component';

describe('SightingsOverTimeComponent', () => {
  let component: SightingsOverTimeComponent;
  let fixture: ComponentFixture<SightingsOverTimeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SightingsOverTimeComponent]
    });
    fixture = TestBed.createComponent(SightingsOverTimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
