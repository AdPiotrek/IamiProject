import {
  ChangeDetectionStrategy,
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output
} from '@angular/core';

import { Photo } from '../../shared/models/photo';

@Component({
  selector: 'app-photo',
  templateUrl: './photo.component.html',
  styleUrls: ['./photo.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PhotoComponent implements OnInit {
  @Input() photo: Photo;
  @Input() photoSize = 'n';
  @Output() authorClicked = new EventEmitter<string>();

  constructor() {
  }

  ngOnInit() {
  }

  onAuthorClicked() {
    this.authorClicked.emit(this.photo);
  }

}
