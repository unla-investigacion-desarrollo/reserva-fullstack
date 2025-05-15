import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

export const authGuard = () => {
  const authService = inject(LoginService);
  const router = inject(Router);

  if (authService.checkLogin()) {
    return true;
  } else {
    router.navigate(['/login']);
    return false;
  }
};
