import { Component, OnInit } from '@angular/core';
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexXAxis,
  ApexTitleSubtitle,
} from 'ng-apexcharts';

export type UserChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  title: ApexTitleSubtitle;
};
@Component({
  selector: 'app-sightings-chart',
  templateUrl: './sightings-chart.component.html',
  styleUrls: ['./sightings-chart.component.css']
})
export class SightingsChartComponent {
  public chartOptions: UserChartOptions;

  ngOnInit(): void {
    // TODO: reemplazar estos datos de ejemplo por tu servicio/API
    const users = ['Ana', 'Bruno', 'Carla', 'Diego', 'Elena'];
    const counts = [12, 8, 15, 5, 9];

    this.chartOptions = {
      series: [
        {
          name: 'Avistamientos',
          data: counts,
        },
      ],
      chart: {
        type: 'bar',
        height: 300,
      },
      xaxis: {
        categories: users,
      },
      title: {
        text: 'Avistamientos por usuario',
        align: 'center',
      },
    };
  }
}
