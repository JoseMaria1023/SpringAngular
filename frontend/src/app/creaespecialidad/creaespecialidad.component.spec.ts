import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreaespecialidadComponent } from './creaespecialidad.component';

describe('CreaespecialidadComponent', () => {
  let component: CreaespecialidadComponent;
  let fixture: ComponentFixture<CreaespecialidadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreaespecialidadComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreaespecialidadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
