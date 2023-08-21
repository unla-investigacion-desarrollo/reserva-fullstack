import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvistamientoPendienteComponent } from './avistamiento-pendiente.component';

describe('AvistamientoPendienteComponent', () => {
  let component: AvistamientoPendienteComponent;
  let fixture: ComponentFixture<AvistamientoPendienteComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AvistamientoPendienteComponent]
    });
    fixture = TestBed.createComponent(AvistamientoPendienteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
