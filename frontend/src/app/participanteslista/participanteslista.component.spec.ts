import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipanteslistaComponent } from './participanteslista.component';

describe('ParticipanteslistaComponent', () => {
  let component: ParticipanteslistaComponent;
  let fixture: ComponentFixture<ParticipanteslistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParticipanteslistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ParticipanteslistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
