import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-search',
  templateUrl: './user-search.component.html',
  styleUrls: ['./user-search.component.scss']
})
export class UserSearchComponent implements OnInit {
  searchForm: FormGroup;
  authors: Observable<any>;

  constructor(private httpClient: HttpClient,
              private formBuilder: FormBuilder,
              private router: Router) {
    this.searchForm = this.formBuilder.group({
      'search': ''
    });
  }

  ngOnInit() {
    this.authors = this.httpClient.get('https://localhost:8443/user/all?name=');
    this.searchForm.get('search').valueChanges
      .pipe(
        debounceTime(300),
        distinctUntilChanged()
      ).subscribe((id) => {
      this.getAuthors(id);
    });
  }

  getAuthors(param) {
    this.authors = this.httpClient.get('https://localhost:8443/user/all?name=' + param);
  }

  goToUserProfile(authorId) {
    this.router.navigateByUrl(`author/${ authorId }`);  }

}
