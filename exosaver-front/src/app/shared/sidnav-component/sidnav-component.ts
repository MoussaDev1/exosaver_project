import { Component, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSidenavModule } from '@angular/material/sidenav';
import { Router } from '@angular/router';

export interface navElements {
  name: string;
  link: string;
  icon?: string; // Optional icon property
}
@Component({
  selector: 'app-sidnav-component',
  imports: [
    MatSidenavModule,
    MatButtonToggleModule,
    MatButtonModule,
    MatCardModule,
    MatProgressBarModule,
  ],
  templateUrl: './sidnav-component.html',
  styleUrl: './sidnav-component.scss',
})
export class SidnavComponent {
  navElements: navElements[] = [
    {
      name: 'Voir les cours',
      link: '',
      icon: '',
    },
    {
      name: 'Créer un cours',
      link: 'create',
    },
  ];

  constructor(private router: Router) {}

  onViewCreateCourse(): void {
    this.router.navigateByUrl('create');
  }
}
