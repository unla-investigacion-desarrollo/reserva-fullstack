import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { 
  trigger,
  transition,
  style,
  animate
} from '@angular/animations';
import { NgForm } from '@angular/forms'; // Importación añadida

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
export class ResetPasswordComponent { // Nombre cambiado
  email: string = '';
  loading: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(form: NgForm) { // Añade NgForm como parámetro
    if (form.invalid) return;
    
    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.authService.sendPasswordResetEmail(this.email).subscribe({
      next: (success) => {
        if (success) {
          this.successMessage = '¡Correo enviado! Revisa tu bandeja de entrada.';
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 3000);
        } else {
          this.errorMessage = 'No encontramos una cuenta con ese correo.';
        }
      },
      error: (error) => {
        this.errorMessage = 'Error al enviar el correo. Intenta nuevamente.';
        console.error('Error:', error);
      },
      complete: () => {
        this.loading = false;
      }
    });
  }
}