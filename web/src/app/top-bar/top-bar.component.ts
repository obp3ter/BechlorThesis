import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
    selector: 'app-top-bar',
    templateUrl: './top-bar.component.html',
    styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

    constructor(private http: HttpClient) {
    }

    ngOnInit() {
    }

    showText(title: string) {
        if (title != "") {
            let body = new FormData();
            body.append('freeText',title);
            console.log(body);
            this.http.post("http://localhost:8080/dbm/command/issue", body).subscribe(resp => {
                alert("Sent!")
            });
        } else {
            alert("Fill the command first!");
        }
    }

}