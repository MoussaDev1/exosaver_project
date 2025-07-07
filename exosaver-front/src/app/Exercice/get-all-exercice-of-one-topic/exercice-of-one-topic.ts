import { ActivatedRoute } from '@angular/router';
import { ExercicesService } from '../../services/exercices-service';
import { exercices } from './../../models/exercices';
import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'exercice-of-one-topic',
  imports: [DatePipe],
  templateUrl: './exercice-of-one-topic.html',
  styleUrl: './exercice-of-one-topic.scss',
})
export class ExerciceOfOneTopic implements OnInit {
  exercices: exercices[] = [];

  constructor(
    private exerciceService: ExercicesService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');

    if (idTopicParam !== null && idCourseParam !== null) {
      const idTopic = Number(idTopicParam);
      const idCourse = Number(idCourseParam);
      this.exerciceService
        .getAllExercicesByTopicId(idCourse, idTopic)
        .subscribe((exercice) => {
          console.log(exercice);

          this.exercices = exercice;
        });
    }
  }
}
