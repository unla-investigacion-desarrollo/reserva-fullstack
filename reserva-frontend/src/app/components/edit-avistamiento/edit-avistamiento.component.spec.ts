import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditAvistamientoComponent } from './edit-avistamiento.component';

describe('EditAvistamientoComponent', () => {
  let component: EditAvistamientoComponent;
  let fixture: ComponentFixture<EditAvistamientoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditAvistamientoComponent]
    });
    fixture = TestBed.createComponent(EditAvistamientoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
