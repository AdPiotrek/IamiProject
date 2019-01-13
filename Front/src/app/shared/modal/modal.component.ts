import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-modal',
  templateUrl: './modal.component.html',
  styleUrls: ['./modal.component.scss']
})
export class ModalComponent {
  @Output() backdropClicked = new EventEmitter<void>();

  constructor() {
  }

  onBackdropClicked() {
    this.backdropClicked.emit();
  }

}
