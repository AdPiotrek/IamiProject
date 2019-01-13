import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';

import * as sunCalc from 'suncalc';

@Component({
  selector: 'app-sun-info',
  templateUrl: './sun-info.component.html',
  styleUrls: ['./sun-info.component.scss'],
  providers: [DatePipe]
})
export class SunInfoComponent implements OnInit {
  days = [
    {
      name: 'Golden Hour Start',
      series: []
    },
    {
      name: 'Golden Hours End',
      series: []
    }
  ]
  xFormatter = (val) => {
    console.log(val)
    return val.toString().slice(0, 2) + ':' + val.toString().slice(2)
  };


  constructor(private datePipe: DatePipe) {
  }

  ngOnInit() {

    navigator.geolocation.getCurrentPosition(({ coords }) => {
      const newDate = new Date();
      const numberOfDaysInMonth = this.getDaysInMonth(newDate.getFullYear(), newDate.getMonth());
      for (let i = 1; i <= numberOfDaysInMonth; i++) {
        console.log();
        this.days[0].series.push({
          name: this.datePipe.transform(new Date(newDate.getFullYear(), newDate.getMonth(), i), 'MxaMM:dd'),
          value: this.datePipe.transform(
            sunCalc.getTimes(new Date(newDate.getFullYear(), newDate.getMonth(), i), coords.latitude, coords.longitude).goldenHour,
            'HHmm'
          )
        })
      }
    })


  }


  getDaysInMonth(year, month) {
    return new Date(year, month, 0).getDate();
  }

}
