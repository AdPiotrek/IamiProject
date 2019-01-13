import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  repeatedPassword: string;
  localization: string;

  registerForm: FormGroup;

  constructor(private httpClient: HttpClient,
              private fb: FormBuilder,
              private router: Router,
              private toastService: ToastrService) {

    this.registerForm = fb.group({
      'firstName': ['', [Validators.required]],
      'lastName': ['', [Validators.required]],
      'email': ['', [Validators.required, Validators.email]],
      'password': ['', [Validators.required, Validators.minLength(6)]],
      'repeatedPassword': ['', [Validators.required]]
    });
  }

  ngOnInit() {

  }

  registerUser() {
    this.httpClient.post('https://localhost:8443/user/register',
      this.registerForm.value
    ).subscribe((resp) => {
      this.toastService.success('Zarejestrowano pomyślnie. Zaloguj się !')
      this.router.navigate(['/login']);
    }, (err) => {
      if (err.status === 409) {
        this.toastService.error('Użytkownik o podanym mailu już istnieje');
        return;
      }
      this.toastService.error('Wystąpił błąd, spróbuj ponownie później.');
    });
  }

}
