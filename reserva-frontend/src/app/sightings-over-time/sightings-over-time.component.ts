import { Component, OnInit } from '@angular/core';
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexTitleSubtitle,
  ApexStroke,
} from 'ng-apexcharts';

export type TimeLineOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  stroke: ApexStroke;
  title: ApexTitleSubtitle;
};
@Component({
  selector: 'app-sightings-over-time',
  templateUrl: './sightings-over-time.component.html',
  styleUrls: ['./sightings-over-time.component.css']
})
export class SightingsOverTimeComponent {
  public lineOptions: TimeLineOptions;

  ngOnInit(): void {
    // Ejemplo de datos: avistamientos diarios en una semana
    const dates = [
      '2025-07-01',
      '2025-07-02',
      '2025-07-03',
      '2025-07-04',
      '2025-07-05',
      '2025-07-06',
      '2025-07-07',
    ];
    const counts = [3, 7, 4, 9, 6, 2, 8];

    this.lineOptions = {
      series: [
        {
          name: 'Avistamientos',
          data: counts,
        },
      ],
      chart: {
        type: 'line',
        height: 300,
        zoom: {
          enabled: true,
        },
      },
      stroke: {
        curve: 'smooth',
      },
      xaxis: {
        categories: dates,
        title: {
          text: 'Fecha',
        },
      },
      title: {
        text: 'Avistamientos en el tiempo',
        align: 'center',
      },
    };
  }
}
