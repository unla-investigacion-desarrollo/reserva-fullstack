import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-reset-contraseña-form',
  templateUrl: './reset-contraseña-form.component.html',
  styleUrls: ['./reset-contraseña-form.component.css']
})
export class ResetContraseñaFormComponent implements OnInit {
  newPassword: string = '';
  confirmPassword: string = '';
  token: string = '';
  loading: boolean = false;
  errorMessage: string = '';
  successMessage: string = '';
  passwordMismatch: boolean = false;
  showPassword: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.route.queryParamMap.subscribe(params => {
      this.token = params.get('token') || '';
      if (!this.token) {
        this.errorMessage = 'Token no válido o faltante';
      }
    });
  }

  onSubmit(): void {
    // Reset messages
    this.errorMessage = '';
    this.successMessage = '';
    this.passwordMismatch = false;
    this.loading = true;

    // Validations
    if (!this.token) {
      this.errorMessage = 'Token no válido';
      return;
    }

    if (this.newPassword !== this.confirmPassword) {
      this.loading = false;
      this.passwordMismatch = true;
      this.errorMessage = 'Las contraseñas no coinciden';
      return;
    }

    if (this.newPassword.length < 8) {
      this.errorMessage = 'La contraseña debe tener al menos 8 caracteres';
      return;
    }

    this.loading = true;

    this.authService.resetPassword(this.token, this.newPassword, this.confirmPassword)
      .subscribe({
        next: () => {
          this.loading = false;
          this.successMessage = '¡Contraseña actualizada correctamente!';
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 2000);
        },
        error: (err) => {
          this.loading = false; // Asegurar que siempre se desactive el loading

          console.error('Error completo:', err);
          if (err.status === 0) {
            this.errorMessage = 'Error de conexión con el servidor';
          } else {
            this.errorMessage = err.message || 'Error al actualizar contraseña';
          }
        },
        complete: () => {
          this.loading = false; // Doble seguridad
        }
      });
  }

  // Método para verificar contraseñas en tiempo real (opcional)
  checkPasswords(): void {
    if (this.newPassword && this.confirmPassword) {
      this.passwordMismatch = this.newPassword !== this.confirmPassword;
    } else {
      this.passwordMismatch = false;
    }
  }
}