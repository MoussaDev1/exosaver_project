import { Router } from '@angular/router';
import { Component, Input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-card-full',
  imports: [MatCardModule, MatProgressBarModule, MatButtonModule],
  templateUrl: './card-full.html',
  styleUrl: './card-full.scss',
})
export class CardFull {
  @Input() title!: string;
  @Input() subtitle?: string;
  @Input() progress?: string;
  @Input() theme?: string;
  @Input() buttonText: string = 'Voir';
  @Input() onButtonClick?: () => void;

  constructor(private router: Router) {}

  onViewCourse(id: number | undefined): void {
    this.router.navigateByUrl(`course/${id}`);
  }
}
