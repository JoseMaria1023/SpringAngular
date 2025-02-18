import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipanteeditarComponent } from './participanteeditar.component';

describe('ParticipanteeditarComponent', () => {
  let component: ParticipanteeditarComponent;
  let fixture: ComponentFixture<ParticipanteeditarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ParticipanteeditarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ParticipanteeditarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
