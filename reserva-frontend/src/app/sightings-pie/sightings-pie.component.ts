import { Component, OnInit } from '@angular/core';
import {
  ApexNonAxisChartSeries,
  ApexChart,
  ApexTitleSubtitle,
  ApexLegend,
} from 'ng-apexcharts';

export type TypePieOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  labels: string[];
  title: ApexTitleSubtitle;
  legend: ApexLegend;
};
@Component({
  selector: 'app-sightings-pie',
  templateUrl: './sightings-pie.component.html',
  styleUrls: ['./sightings-pie.component.css']
})
export class SightingsPieComponent {
  public pieOptions: TypePieOptions;

  ngOnInit(): void {
    // TODO: reemplaza con datos reales de tu API
    const labels = ['Flora', 'Fauna'];
    const values = [30, 45];

    this.pieOptions = {
      series: values,
      chart: {
        type: 'pie',
        height: 300,
      },
      labels,
      title: {
        text: 'Proporción Flora vs Fauna',
        align: 'center',
      },
      legend: {
        position: 'bottom',
      },
    };
  }
}
