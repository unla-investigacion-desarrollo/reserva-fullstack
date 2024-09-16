import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VerAvistamientoComponent } from './ver-avistamiento.component';

describe('EditAvistamientoComponent', () => {
  let component: VerAvistamientoComponent;
  let fixture: ComponentFixture<VerAvistamientoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [VerAvistamientoComponent]
    });
    fixture = TestBed.createComponent(VerAvistamientoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
