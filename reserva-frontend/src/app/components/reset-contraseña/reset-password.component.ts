import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { trigger, transition, style, animate } from '@angular/animations';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css'],
  animations: [
    trigger('cardAnimation', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(30px) scale(0.95)' }),
        animate('600ms cubic-bezier(0.25, 0.46, 0.45, 0.94)', 
          style({ opacity: 1, transform: 'translateY(0) scale(1)' }))
      ])
    ])
  ]
})
export class ResetPasswordComponent {
  email: string = '';
  loading: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(form: NgForm) {
  console.log('Form submitted:', { email: this.email, valid: form.valid, errors: form.controls['email'].errors });
  if (form.invalid) {
    this.errorMessage = 'Por favor ingresa un correo electrónico válido';
    return;
  }
  this.loading = true;
  this.errorMessage = '';
  this.successMessage = '';
  this.authService.sendPasswordResetEmail(this.email).subscribe({
    next: (success) => {
      console.log('Auth service response:', success);
      if (success) {
        this.successMessage = '¡Correo enviado! Revisa tu bandeja de entrada.';
        form.resetForm();
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 3000);
      } else {
        this.errorMessage = 'No encontramos una cuenta con ese correo.';
      }
    },
    error: (error) => {
      console.error('Error:', error);
      this.errorMessage = error.error?.message || 'Error al enviar el correo. Intenta nuevamente.';
    },
    complete: () => {
      console.log('Request completed');
      this.loading = false;
    }
  });
}
}