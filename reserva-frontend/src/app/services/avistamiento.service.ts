import { Injectable } from '@angular/core';
import { LoremIpsum, loremIpsum } from "lorem-ipsum";

@Injectable({
  providedIn: 'root'
})
export class AvistamientoService {

  constructor() { }

  getAvistamientosPendientes() {
    var obj = [
      { id: '1', tipo: 'Arbol', especie: 'Jacaranda', descripcion: 'Descripcion de la especie.', subespecie: 'Jacaranda mimosifolia', familia: 'Bignoniaceae', number: '0011', user: 'Nicolas', location: '34 35 36: 16 45 48', avistDate: new Date(2023,7,1) },
      { id: '2', especie: 'Castor', number: '0022', user: 'Federico', location: '34 22 77: 11 22 24', avistDate: new Date(2023,7,15) },
      { id: '3', especie: 'Lechuza', number: '0033', user: 'Martin', location: '76 16 36: 42 12 18', avistDate: new Date(2023,7,14)},
      { id: '4', especie: 'Buho', number: '0044', user: 'Juan', location: '34 37 56: 16 15 12', avistDate: new Date(2023,7,13) },
      { id: '5', especie: 'Ciervo', number: '0055', user: 'Nicolas', location: '34 35 73: 16 54 43', avistDate: new Date(2023,7,12) }
    ];
    return JSON.stringify(obj);
  }

  getAvistamientosAprobados() {
    var obj = [
      { id: '1', tipo: 'Arbol', especie: 'Jacaranda', descripcion: 'Descripcion de la especie.', subespecie: 'Jacaranda mimosifolia', familia: 'Bignoniaceae', number: '0011', user: 'Nicolas', location: '34 35 36: 16 45 48', avistDate: new Date(2023,7,1) },
      { id: '2', especie: 'Castor', number: '0022', user: 'Federico', location: '34 22 77: 11 22 24', avistDate: new Date(2023,7,15) },
      { id: '3', especie: 'Lechuza', number: '0033', user: 'Martin', location: '76 16 36: 42 12 18', avistDate: new Date(2023,7,14)},
      { id: '4', especie: 'Buho', number: '0044', user: 'Juan', location: '34 37 56: 16 15 12', avistDate: new Date(2023,7,13) },
      { id: '5', especie: 'Ciervo', number: '0055', user: 'Nicolas', location: '34 35 73: 16 54 43', avistDate: new Date(2023,7,12) }
    ];
    return JSON.stringify(obj);
  }

  getAvistamientosReprobados() {
    var obj = [
      { id: '1', tipo: 'Arbol', especie: 'Jacaranda', descripcion: 'Descripcion de la especie.', subespecie: 'Jacaranda mimosifolia', familia: 'Bignoniaceae', number: '0011', user: 'Nicolas', location: '34 35 36: 16 45 48', avistDate: new Date(2023,7,1) },
      { id: '2', especie: 'Castor', number: '0022', user: 'Federico', location: '34 22 77: 11 22 24', avistDate: new Date(2023,7,15) },
      { id: '3', especie: 'Lechuza', number: '0033', user: 'Martin', location: '76 16 36: 42 12 18', avistDate: new Date(2023,7,14)},
      { id: '4', especie: 'Buho', number: '0044', user: 'Juan', location: '34 37 56: 16 15 12', avistDate: new Date(2023,7,13) },
      { id: '5', especie: 'Ciervo', number: '0055', user: 'Nicolas', location: '34 35 73: 16 54 43', avistDate: new Date(2023,7,12) }
    ];
    return JSON.stringify(obj);
  }

  getAvistamientoById(id: string) {

    switch(id){
      case '1':
        return JSON.stringify({ id: '1', tipo: 'Arbol', descripcion: loremIpsum({count: 50}), especie: 'Jacaranda', subespecie: 'Jacaranda mimosifolia', familia: 'Bignoniaceae', number: '0011', user: 'Nicolas', location: '34 35 36: 16 45 48', avistDate: new Date(2023,7,1) });
        break;
      case '2':
        return JSON.stringify({ id: '2', tipo: 'Tipo animal', animal: 'Castor', descripcion: loremIpsum({count: 50}), especie: 'Animal', subespecie: 'Subespecie Animal', familia: 'Familia Animal', number: '0022', user: 'Federico', location: '34 22 77: 11 22 24', avistDate: new Date(2023,7,15) });
        break;
      case '3':
        return JSON.stringify({ id: '3',tipo: 'Tipo animal',  animal: 'Lechuza', descripcion: loremIpsum({count: 50}), especie: 'Animal', subespecie: 'Subespecie Animal', familia: 'Familia Animal', number: '0033', user: 'Martin', location: '76 16 36: 42 12 18', avistDate: new Date(2023,7,14)});
        break;
      case '4':
        return JSON.stringify({ id: '4',tipo: 'Tipo animal',  animal: 'Buho', descripcion: loremIpsum({count: 50}), especie: 'Animal', subespecie: 'Subespecie Animal', familia: 'Familia Animal', number: '0044', user: 'Juan', location: '34 37 56: 16 15 12', avistDate: new Date(2023,7,13) });
        break;
      case '5':
        return JSON.stringify({ id: '5',tipo: 'Tipo animal',  animal: 'Ciervo', descripcion: loremIpsum({count: 50}), especie: 'Animal', subespecie: 'Subespecie Animal', familia: 'Familia Animal', number: '0055', user: 'Nicolas', location: '34 35 73: 16 54 43', avistDate: new Date(2023,7,12) });
        break;   
      default:
        return JSON.stringify("")

    }
  }
}
