import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CrearitemComponent } from './crearitem.component';

describe('CrearitemComponent', () => {
  let component: CrearitemComponent;
  let fixture: ComponentFixture<CrearitemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CrearitemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CrearitemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
