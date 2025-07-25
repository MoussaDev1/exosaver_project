import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';
import { exercices } from '../../models/exercices';
import { FeynmanStatus } from '../../models/feynmanStatus';
import { ExercicesService } from '../../services/exercices-service';
import { DatePipe } from '@angular/common';
import { UpdateFeynmanComponent } from '../update-feynman-component/update-feynman-component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HeaderModule } from '../../shared/header-module/header-module';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-one-exercice-component',
  imports: [
    UpdateFeynmanComponent,
    MatFormFieldModule,
    HeaderModule,
    MatButtonModule,
  ],
  templateUrl: './one-exercice-component.html',
  styleUrl: './one-exercice-component.scss',
})
export class OneExerciceComponent implements OnInit {
  exercice: exercices = {
    title: '',
    description: '',
    solution: '',
  };
  courseId!: number;
  topicId!: number;

  constructor(
    private exerciceService: ExercicesService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');
    const idExerciceParam = this.route.snapshot.paramMap.get('idExercice');

    if (idCourseParam && idTopicParam && idExerciceParam) {
      const idCourse = Number(idCourseParam);
      const idTopic = Number(idTopicParam);
      const idExercice = Number(idExerciceParam);
      this.onGetExerciceById(idCourse, idTopic, idExercice);

      this.courseId = idCourse; // Store courseId for later use
      this.topicId = idTopic; // Store topicId for later use
      console.log(`Course ID: ${this.courseId}`);
      console.log(`Topic ID: ${this.topicId}`);
    }
  }

  onGetExerciceById(
    idCourse: number,
    idTopic: number,
    idExercice: number
  ): void {
    this.exerciceService
      .getExerciceById(idCourse, idTopic, idExercice)
      .subscribe({
        next: (exercice) => {
          this.exercice = exercice;
        },
        error: (error) => {
          console.error('Error fetching exercice:', error);
        },
      });
  }

  onViewUpdateExercice(): void {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');
    const idExerciceParam = this.route.snapshot.paramMap.get('idExercice');

    if (idCourseParam && idTopicParam && idExerciceParam) {
      const idCourse = Number(idCourseParam);
      const idTopic = Number(idTopicParam);
      const idExercice = Number(idExerciceParam);
      // Navigate to the edit page for the exercice
      this.router.navigate([
        `/course/${idCourse}/topic/${idTopic}/exercice/edit/${idExercice}`,
      ]);
    }
  }

  onDeleteExercice(): void {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');
    const idExerciceParam = this.route.snapshot.paramMap.get('idExercice');

    if (idCourseParam && idTopicParam && idExerciceParam) {
      const idCourse = Number(idCourseParam);
      const idTopic = Number(idTopicParam);
      const idExercice = Number(idExerciceParam);

      this.exerciceService
        .deleteExercice(idCourse, idTopic, idExercice)
        .subscribe({
          next: () => {
            this.router.navigate([`/course/${idCourse}/topic/${idTopic}`]);
          },
          error: (error) => {
            console.error('Error deleting exercice:', error);
          },
        });
    }
  }

  onFeynmanStatusUpdated(newStatus: FeynmanStatus) {
    this.exercice = {
      ...this.exercice,
      feynmanStatus: newStatus,
    };
  }

  onReturnToTopic(topicId?: number, courseId?: number) {
    if (topicId !== undefined && courseId !== undefined) {
      this.router.navigate([`/course/${courseId}/topic/${topicId}`]);
    } else {
      console.error('No topic or course ID found to return to.');
    }
  }
}
