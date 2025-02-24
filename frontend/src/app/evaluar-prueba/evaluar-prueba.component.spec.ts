import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EvaluarPruebaComponent } from './evaluar-prueba.component';

describe('EvaluarPruebaComponent', () => {
  let component: EvaluarPruebaComponent;
  let fixture: ComponentFixture<EvaluarPruebaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EvaluarPruebaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EvaluarPruebaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
