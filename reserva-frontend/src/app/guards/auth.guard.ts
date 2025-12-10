import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  private readonly tokenKey = 'auth_token';

  constructor(private router: Router) {}

  canActivate(): boolean | UrlTree {
    const token = localStorage.getItem(this.tokenKey);
    if (!token || token.trim().length === 0) {
      return this.router.createUrlTree(['/login']);
    }

    const hasRequiredRole = this.hasRole(token, 'ROLE_PERSONAL_RESERVA');
    if (hasRequiredRole) {
      return true;
    }
    return this.router.createUrlTree(['/login']);
  }

  private hasRole(token: string, requiredRole: string): boolean {
    try {
      const parts = token.split('.');
      if (parts.length !== 3) {
        return false;
      }
      const payloadSegment = parts[1];
      const json = this.base64UrlDecode(payloadSegment);
      const payload = JSON.parse(json);
      const role = payload.role ?? payload['roles'] ?? payload['authorities'];
      if (Array.isArray(role)) {
        return role.includes(requiredRole);
      }
      return role === requiredRole;
    } catch {
      return false;
    }
  }

  private base64UrlDecode(input: string): string {
    let str = input.replace(/-/g, '+').replace(/_/g, '/');
    const pad = str.length % 4;
    if (pad === 2) str += '==';
    else if (pad === 3) str += '=';
    else if (pad !== 0) {
      throw new Error('Invalid base64url string');
    }
    try {
      return decodeURIComponent(
        Array.prototype.map
          .call(atob(str), (c: string) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join('')
      );
    } catch (e) {
      return atob(str);
    }
  }
}
