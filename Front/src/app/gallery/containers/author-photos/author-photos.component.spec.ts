import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorPhotosComponent } from './author-photos.component';

describe('AuthorPhotosComponent', () => {
  let component: AuthorPhotosComponent;
  let fixture: ComponentFixture<AuthorPhotosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [AuthorPhotosComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorPhotosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
