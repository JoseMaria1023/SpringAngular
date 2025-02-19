import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearexpertoComponent } from './crearexperto.component';

describe('CrearexpertoComponent', () => {
  let component: CrearexpertoComponent;
  let fixture: ComponentFixture<CrearexpertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearexpertoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearexpertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
