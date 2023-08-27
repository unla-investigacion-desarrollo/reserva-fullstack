import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvistamientoReprobadoComponent } from './avistamiento-reprobado.component';

describe('AvistamientoReprobadoComponent', () => {
  let component: AvistamientoReprobadoComponent;
  let fixture: ComponentFixture<AvistamientoReprobadoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvistamientoReprobadoComponent]
    });
    fixture = TestBed.createComponent(AvistamientoReprobadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
