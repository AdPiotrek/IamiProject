import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DogsPhotosComponent } from './dogs-photos.component';

describe('DogsPhotosComponent', () => {
  let component: DogsPhotosComponent;
  let fixture: ComponentFixture<DogsPhotosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DogsPhotosComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DogsPhotosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
