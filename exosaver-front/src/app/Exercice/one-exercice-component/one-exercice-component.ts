import { ActivatedRoute, RouterLink } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { exercices } from '../../models/exercices';
import { FeynmanStatus } from '../../models/feynmanStatus';
import { ExercicesService } from '../../services/exercices-service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-one-exercice-component',
  imports: [DatePipe],
  templateUrl: './one-exercice-component.html',
  styleUrl: './one-exercice-component.scss',
})
export class OneExerciceComponent implements OnInit {
  exercice: exercices = {
    title: '',
    description: '',
    solution: '',
  };

  constructor(
    private exerciceService: ExercicesService,
    private route: ActivatedRoute
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
}
