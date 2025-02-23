import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarpruebaComponent } from './editarprueba.component';

describe('EditarpruebaComponent', () => {
  let component: EditarpruebaComponent;
  let fixture: ComponentFixture<EditarpruebaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarpruebaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarpruebaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
