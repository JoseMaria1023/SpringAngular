import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditarexpertoComponent } from './editarexperto.component';

describe('EditarexpertoComponent', () => {
  let component: EditarexpertoComponent;
  let fixture: ComponentFixture<EditarexpertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EditarexpertoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EditarexpertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
