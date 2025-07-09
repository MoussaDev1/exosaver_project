import { Component, EventEmitter, Output } from '@angular/core';
import { FeynmanStatus } from '../../models/feynmanStatus';
import { FormsModule, NgForm } from '@angular/forms';
import { MatSelect, MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { exercices } from '../../models/exercices';
import { ExercicesService } from '../../services/exercices-service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-update-feynman-component',
  imports: [FormsModule, MatSelectModule, MatFormFieldModule],
  templateUrl: './update-feynman-component.html',
  styleUrl: './update-feynman-component.scss',
})
export class UpdateFeynmanComponent {
  feynmanStatuses = Object.values(FeynmanStatus);

  exercice: exercices = {
    title: '',
    description: '',
    solution: '',
    feynmanStatus: FeynmanStatus.NOT_STARTED,
  };

  @Output() statusUpdated = new EventEmitter<FeynmanStatus>();

  statusLabels = {
    NOT_STARTED: 'à faire',
    TO_EXPLAIN: 'à expliquer',
    EXPLAINED_OK: 'expliqué correctement',
    EXPLAINED_BADLY: 'expliqué incorrectement',
    TO_REVIEW: 'à revoir',
    REVIEW_OK: 'revu correctement',
    NEEDS_WORK: 'besoin de travail',
    REVIEW_FAILED: 'revu incorrectement',
  };

  constructor(
    private exerciceService: ExercicesService,
    private route: ActivatedRoute
  ) {}

  onSumbitFeynmanForm(form: NgForm): void {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');
    const idExerciceParam = this.route.snapshot.paramMap.get('idExercice');
    const exerciceStatus = this.exercice.feynmanStatus;

    if (idCourseParam && idTopicParam && idExerciceParam && exerciceStatus) {
      const idCourse = Number(idCourseParam);
      const idTopic = Number(idTopicParam);
      const idExercice = Number(idExerciceParam);

      this.exerciceService
        .updateFeynmanStatus(idCourse, idTopic, idExercice, exerciceStatus)
        .subscribe({
          next: () => {
            this.statusUpdated.emit(this.exercice.feynmanStatus);
            // Optionally, you can navigate to another page or show a success message
          },
        });
    }
  }
}
