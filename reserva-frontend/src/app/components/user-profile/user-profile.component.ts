import { Component, OnInit, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserService } from '../../services/user.service';
import { AvistamientoService } from '../../services/avistamiento.service';
import { lastValueFrom } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { ImageModalComponent } from '../image-modal/image-modal.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {
  user: any = {
    name: '',
    lastname: '',
    username: '',
    email: '',
    role: '',
    avatarUrl: '',
    location: '',
    joinDate: new Date()
  };

  userStats = {
    totalSightings: 0,
    approvedSightings: 0,
    pendingSightings: 0
  };

  sightings: any[] = [];
  filteredSightings: any[] = [];
  isLoading = true;
  activeTab = 'all';
  errorMessage: string = '';

  constructor(
    private userService: UserService,
    private avistamientoService: AvistamientoService,
    private dialog: MatDialog,
    private router: Router
  ) {}

  async ngOnInit() {
    await this.loadUserData();
    await this.loadUserSightings();
    this.isLoading = false;
  }

  async loadUserData() {
  try {
    const userData = await lastValueFrom(this.userService.getCurrentUser());
    this.user = {
      id: userData.id,
      name: userData.name,
      username: userData.username,
      email: userData.email,
      role: 'Usuario', 
      lastname: '',
      avatarUrl: '',
      location: 'Ubicación no especificada',
      joinDate: new Date()
    };
  } catch (error) {
    console.error('Error loading user data:', error);
    this.errorMessage = 'Error al cargar datos del usuario';
  }
}

  async loadUserSightings() {
    try {
      const sightingsData = await lastValueFrom(
        this.avistamientoService.getSightingsByUser(this.user.id)
      );
      
      this.sightings = sightingsData.map(sighting => ({
        ...sighting,
        date: new Date(sighting.createdAt),
        status: this.getSightingStatus(sighting)
      }));
      
      this.calculateStats();
      this.filterSightings();
    } catch (error) {
      console.error('Error al cargar avistamientos:', error);
    }
  }

  calculateStats() {
    this.userStats = {
      totalSightings: this.sightings.length,
      approvedSightings: this.sightings.filter(s => s.status === 'approved').length,
      pendingSightings: this.sightings.filter(s => s.status === 'pending').length
    };
  }

  filterSightings() {
    switch (this.activeTab) {
      case 'approved':
        this.filteredSightings = this.sightings.filter(s => s.status === 'approved');
        break;
      case 'pending':
        this.filteredSightings = this.sightings.filter(s => s.status === 'pending');
        break;
      default:
        this.filteredSightings = [...this.sightings];
    }
  }

  onTabChange(tab: string) {
    this.activeTab = tab;
    this.filterSightings();
  }

  getSightingStatus(sighting: any): string {
    // Implementa la lógica para determinar el estado según tu API
    return sighting.approved ? 'approved' : 
           sighting.rejected ? 'rejected' : 'pending';
  }

  getStatusIcon(status: string): string {
    switch (status) {
      case 'approved': return 'fas fa-check-circle';
      case 'rejected': return 'fas fa-times-circle';
      default: return 'fas fa-clock';
    }
  }

  getStatusText(status: string): string {
    switch (status) {
      case 'approved': return 'Aprobado';
      case 'rejected': return 'Rechazado';
      default: return 'Pendiente';
    }
  }

  async viewSighting(sightingId: number) {
    try {
      const imageUrls = await lastValueFrom(
        this.avistamientoService.getImageUrlsBySighting(sightingId)
      );

      if (!imageUrls || imageUrls.length === 0) {
        throw new Error('No hay imágenes disponibles');
      }

      this.dialog.open(ImageModalComponent, {
        width: '80vw',
        maxWidth: '1200px',
        panelClass: 'custom-image-modal',
        data: { 
          images: imageUrls.map(url => ({ url })),
          sightingId: sightingId
        }
      });
    } catch (error: any) {
      console.error('Error al cargar imágenes:', error);
      alert(error.message);
    }
  }

  handleImageError(event: any) {
    event.target.src = 'assets/images/default-avatar.png';
  }

  trackBySightingId(index: number, sighting: any): number {
    return sighting.id;
  }
}

@NgModule({
  declarations: [UserProfileComponent],
  imports: [
    CommonModule
  ]
})
export class UserProfileModule { }