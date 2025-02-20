import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListaexpertoComponent } from './listaexperto.component';

describe('ListaexpertoComponent', () => {
  let component: ListaexpertoComponent;
  let fixture: ComponentFixture<ListaexpertoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListaexpertoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListaexpertoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
