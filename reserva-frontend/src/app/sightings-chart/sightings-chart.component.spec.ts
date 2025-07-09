import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SightingsChartComponent } from './sightings-chart.component';

describe('SightingsChartComponent', () => {
  let component: SightingsChartComponent;
  let fixture: ComponentFixture<SightingsChartComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SightingsChartComponent]
    });
    fixture = TestBed.createComponent(SightingsChartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
