import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListarespecialidadComponent } from './listarespecialidad.component';

describe('ListarespecialidadComponent', () => {
  let component: ListarespecialidadComponent;
  let fixture: ComponentFixture<ListarespecialidadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListarespecialidadComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListarespecialidadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
