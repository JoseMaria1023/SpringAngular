import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearpruebaComponent } from './crearprueba.component';

describe('CrearpruebaComponent', () => {
  let component: CrearpruebaComponent;
  let fixture: ComponentFixture<CrearpruebaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearpruebaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearpruebaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
