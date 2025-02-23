import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListapruebaComponent } from './listaprueba.component';

describe('ListapruebaComponent', () => {
  let component: ListapruebaComponent;
  let fixture: ComponentFixture<ListapruebaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListapruebaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListapruebaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
