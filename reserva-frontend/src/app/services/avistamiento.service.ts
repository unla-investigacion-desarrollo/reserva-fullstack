import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AvistamientoService {

  constructor() { }

  getAvistamientosPendientes() {
    var obj = [
      { animal: 'Pajaro', number: '0011', user: 'Nicolas', location: '34 35 36: 16 45 48', avistDate: new Date(2023,7,1) },
      { animal: 'Castor', number: '0022', user: 'Federico', location: '34 22 77: 11 22 24', avistDate: new Date(2023,7,15) },
      { animal: 'Lechuza', number: '0033', user: 'Martin', location: '76 16 36: 42 12 18', avistDate: new Date(2023,7,14)},
      { animal: 'Buho', number: '0044', user: 'Juan', location: '34 37 56: 16 15 12', avistDate: new Date(2023,7,13) },
      { animal: 'Ciervo', number: '0055', user: 'Nicolas', location: '34 35 73: 16 54 43', avistDate: new Date(2023,7,12) }
    ];
    return JSON.stringify(obj);;
  }
}
