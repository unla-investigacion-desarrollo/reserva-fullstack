import { Component } from '@angular/core';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { AvistamientoService } from '../../services/avistamiento.service';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponentComponent } from '../../components/dialog-component/dialog-component.component';

@Component({
  selector: 'app-edit-avistamiento',
  templateUrl: './edit-avistamiento.component.html',
  styleUrls: ['./edit-avistamiento.component.css']
})
export class EditAvistamientoComponent {
  id: string | null = null;
  avistamiento: any = null;
  isLoading: boolean = true;

  constructor(
    private avistamientoService: AvistamientoService,
    private dialog: MatDialog,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void> {
    try {
      this.isLoading = true;
      this.activatedRoute.params.subscribe(async (params: Params) => {
        if (params && params['id']) {
          this.id = params['id'];
          this.avistamiento = await this.avistamientoService.getAvistamientoById(this.id);
          console.log('Avistamiento loaded:', this.avistamiento);
          this.isLoading = false;
        } else {
          console.error('No ID found in route parameters');
          this.isLoading = false;
          this.router.navigate(['/home']);
        }
      });
    } catch (error) {
      console.error('Error loading avistamiento:', error);
      this.isLoading = false;
      this.router.navigate(['/home']);
    }
  }

  async Aprobar() {
    if (!this.id) {
      console.error('No ID available for approving avistamiento');
      return;
    }
    try {
      await this.avistamientoService.updateStatusAvistamiento(
        JSON.parse(localStorage.getItem('userData')).data.id,
        Number(this.id),
        'aprobado'
      );

      console.log('Opening dialog with data:', { type: 0 });
      const dialogRef = this.dialog.open(DialogComponentComponent, {
        data: { type: 0 },
        width: '600px', // Increased from 400px to 600px
        autoFocus: true
      });

      dialogRef.afterClosed().subscribe((result) => {
        console.log('Dialog closed with result:', result);
        if (result === 1) {
          this.router.navigate(['/avistamientos_aprobados']);
        } else {
          this.router.navigate(['/home']);
        }
      });
    } catch (error) {
      console.error('Error approving avistamiento:', error);
    }
  }

  async Rechazar() {
    if (!this.id) {
      console.error('No ID available for rejecting avistamiento');
      return;
    }
    try {
      await this.avistamientoService.updateStatusAvistamiento(
        JSON.parse(localStorage.getItem('userData')).data.id,
        Number(this.id),
        'reprobado'
      );

      console.log('Opening dialog with data:', { type: 1 });
      const dialogRef = this.dialog.open(DialogComponentComponent, {
        data: { type: 1 },
        width: '600px', // Increased from 400px to 600px
        autoFocus: true
      });

      dialogRef.afterClosed().subscribe((result) => {
        console.log('Dialog closed with result:', result);
        this.router.navigate(['/home']);
      });
    } catch (error) {
      console.error('Error rejecting avistamiento:', error);
    }
  }
}