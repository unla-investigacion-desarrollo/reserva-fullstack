import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SightingsPieComponent } from './sightings-pie.component';

describe('SightingsPieComponent', () => {
  let component: SightingsPieComponent;
  let fixture: ComponentFixture<SightingsPieComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SightingsPieComponent]
    });
    fixture = TestBed.createComponent(SightingsPieComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
