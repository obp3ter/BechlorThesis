import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";

import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
    selector: 'app-top-bar',
    templateUrl: './top-bar.component.html',
    styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent implements OnInit {

    constructor(private http: HttpClient, private _snackBar: MatSnackBar) {
    }

    ngOnInit() {
    }

    showText(title: string) {
        if (title != "") {
            let body = new FormData();
            body.append('freeText',title);
            console.log(body);
            this.http.post("http://localhost:8080/api/command/issue", body).subscribe(resp => {
                this._snackBar.open("Command issued!", "", {
                    duration: 2000,
                    panelClass:['snack-style']
                });
            });
        } else {
            alert("Fill the command first!");
        }
    }

}