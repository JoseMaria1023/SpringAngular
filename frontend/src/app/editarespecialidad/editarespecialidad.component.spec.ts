import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarespecialidadComponent } from './editarespecialidad.component';

describe('EditarespecialidadComponent', () => {
  let component: EditarespecialidadComponent;
  let fixture: ComponentFixture<EditarespecialidadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarespecialidadComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarespecialidadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
