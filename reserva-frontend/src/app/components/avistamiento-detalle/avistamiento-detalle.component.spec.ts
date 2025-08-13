import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvistamientoDetalleComponent } from './avistamiento-detalle.component';

describe('AvistamientoDetalleComponent', () => {
  let component: AvistamientoDetalleComponent;
  let fixture: ComponentFixture<AvistamientoDetalleComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvistamientoDetalleComponent]
    });
    fixture = TestBed.createComponent(AvistamientoDetalleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
