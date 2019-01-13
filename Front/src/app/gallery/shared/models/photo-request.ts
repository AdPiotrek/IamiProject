import { Photo } from './photo';

export interface PhotoRequest {
  photos: {
    photo: Photo[];
    perpage: number;
    page: number;
    pages: number;
  };
}
