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
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PhotosComponent implements OnInit {
  @Input() photos: Photo[];
  @Input() showDetails = true;
  @Input() hasDelete = false;
  @Output() authorClicked = new EventEmitter<string>();
  @Output() photoClicked = new EventEmitter<Photo>();
  @Output() deleteClicked = new EventEmitter<string>();

  constructor() {
  }

  ngOnInit() {
  }

  onAuthorClicked(authorId: string) {
    this.authorClicked.emit(authorId);
  }

  onPhotoClicked(photo: Photo) {
    this.photoClicked.emit(photo);
  }

  photoIdentity(index, photo) {
    return photo.id;
  }

  onDeleteClicked(id) {
    this.deleteClicked.emit(id);
  }
}
