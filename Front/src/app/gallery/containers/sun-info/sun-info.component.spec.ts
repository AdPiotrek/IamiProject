import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SunInfoComponent } from './sun-info.component';

describe('SunInfoComponent', () => {
  let component: SunInfoComponent;
  let fixture: ComponentFixture<SunInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SunInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SunInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
