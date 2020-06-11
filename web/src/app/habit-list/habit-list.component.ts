import {Component} from '@angular/core';

import {products} from '../products';
import {Habit} from "./habit";
import {HabitProperty} from "./habit-property";
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'app-habit-list',
    templateUrl: './habit-list.component.html',
    styleUrls: ['./habit-list.component.css']
})
export class HabitListComponent {
    habits: Array<Habit>;

    constructor(private http: HttpClient) {
        this.getList()
    }

    getList(){
        this.http.get("http://localhost:8080/api/habit").subscribe((habits : Habit[]) =>{this.habits = habits;});
    }

    public propertiesStingBuilder(habit:Habit):string{
        var properties: Array<HabitProperty> =habit.properties;
        var habitPropertiesSorted: Array<Array<HabitProperty>> = [];

        properties.forEach((property)=>{
            var index= -1;
            for(var i=0;i<habitPropertiesSorted.length;++i ){
                if(habitPropertiesSorted[i][0]
                    .propertyName == property.propertyName && index ==-1)
                {
                    index=i;
                    habitPropertiesSorted[i].push(property);
                }
            }
            if(index==-1)
                habitPropertiesSorted.push([property]);
        });

        var result="...\""+habit.freeText+"\"";

        habitPropertiesSorted.forEach((list)=>{
            switch (list[0].propertyName) {
                case ("dayOfWeek"):
                    result+=" on "
                    if(list.length==1){
                        result+=list[0].value1;
                    }
                    else {
                        list.forEach((value, index) => {
                            result+=value.value1;
                            if(index<list.length-2)
                                result+=", ";
                            if(index==list.length-2)
                                result+=" and ";
                        })
                    }
                    break;
                case ("timeOfDay"):
                    result+=" at "
                    if(list.length==1){
                        let hour = Math.floor(parseInt(list[0].value1)/60/60);
                        let minute = Math.floor(parseInt(list[0].value1)%(60*60)/60);
                        let second = parseInt(list[0].value1)%60;
                        result+=(hour<10?"0"+hour:hour)+":" + (minute<10?"0"+minute:minute) + ":"+ (second<10?"0"+second:second);
                    }
                    else {
                        list.forEach((value, index) => {
                            let hour = Math.floor(parseInt(value.value1)/60/60);
                            let minute = Math.floor(parseInt(value.value1)%(60*60)/60);
                            let second = parseInt(value.value1)%60;
                            result+=(hour<10?"0"+hour:hour)+":" + (minute<10?"0"+minute:minute) + ":"+ (second<10?"0"+second:second);
                            if(index<list.length-2)
                                result+=", ";
                            if(index==list.length-2)
                                result+=" and ";
                        })
                    }
                    break;

            }
        })
        result+="."
        return result;
    }

    public pauseHabit(habit:Habit) {
        let body = new FormData();
        body.append('UUID',habit.id);
        console.log(habit.id);
        this.http.post("http://localhost:8080/api/habit/pause", body).subscribe(()=>{window.alert(!habit.active?"Unpaused":"Paused"); this.getList()})
    }
    public deleteHabit(habit:Habit) {
        let body = new FormData();
        body.append('UUID',habit.id);
        console.log(habit.id);
        this.http.post("http://localhost:8080/api/habit/delete", body).subscribe(()=>{window.alert("Deleted!"); this.getList()});
    }

}
