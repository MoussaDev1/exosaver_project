import { ActivatedRoute, Router } from '@angular/router';
import { ExercicesService } from '../../services/exercices-service';
import { exercices } from './../../models/exercices';
import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { Topic } from '../../models/topics';

@Component({
  selector: 'exercice-of-one-topic',
  imports: [DatePipe],
  templateUrl: './exercice-of-one-topic.html',
  styleUrl: './exercice-of-one-topic.scss',
})
export class ExerciceOfOneTopic implements OnInit {
  exercices: exercices[] = [];
  topic: Topic = {
    title: '',
    description: '',
  };

  constructor(
    private exerciceService: ExercicesService,
    private route: ActivatedRoute,
    private router: Router
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
          this.exercices = exercice;
        });
    }
  }

  onViewOneExercice(idExercice?: number): void {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');
    if (idCourseParam !== null && idTopicParam !== null) {
      const idCourse = Number(idCourseParam);
      const idTopic = Number(idTopicParam);
      this.router.navigate([
        `course/${idCourse}/topic/${idTopic}/exercice/${idExercice}`,
      ]);
    }
  }
}
