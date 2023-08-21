import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvistamientoAprobadoComponent } from './avistamiento-aprobado.component';

describe('AvistamientoAprobadoComponent', () => {
  let component: AvistamientoAprobadoComponent;
  let fixture: ComponentFixture<AvistamientoAprobadoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvistamientoAprobadoComponent]
    });
    fixture = TestBed.createComponent(AvistamientoAprobadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
