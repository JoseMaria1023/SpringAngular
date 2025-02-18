import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantescrearComponent } from './participantescrear.component';

describe('ParticipantescrearComponent', () => {
  let component: ParticipantescrearComponent;
  let fixture: ComponentFixture<ParticipantescrearComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParticipantescrearComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ParticipantescrearComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
