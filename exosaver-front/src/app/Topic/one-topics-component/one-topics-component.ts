import { Component, OnInit } from '@angular/core';
import { TopicService } from '../../services/topic-service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Topic } from '../../models/topics';
import { ExerciceOfOneTopic } from '../../Exercice/get-all-exercice-of-one-topic/exercice-of-one-topic';
import { MatCardModule } from '@angular/material/card';
import { ExercicesService } from '../../services/exercices-service';
import { MatButtonModule } from '@angular/material/button';
import { HeaderModule } from '../../shared/header-module/header-module';

@Component({
  selector: 'app-one-topics-component',
  imports: [
    RouterLink,
    ExerciceOfOneTopic,
    MatCardModule,
    MatButtonModule,
    HeaderModule,
  ],
  templateUrl: './one-topics-component.html',
  styleUrl: './one-topics-component.scss',
})
export class OneTopicsComponent implements OnInit {
  topic: Topic = {
    id: undefined,
    title: '',
    description: '',
    courseId: undefined,
  };
  exerciceCount = 0;

  constructor(
    private topicService: TopicService,
    private route: ActivatedRoute,
    private router: Router,
    private exerciceService: ExercicesService
  ) {}

  ngOnInit(): void {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');

    if (idCourseParam !== null && idTopicParam !== null) {
      const idCourse = Number(idCourseParam);
      const idTopic = Number(idTopicParam);
      if (!isNaN(idTopic)) {
        this.onGetTopicById(idCourse, idTopic);
        this.exerciceService
          .getAllExercicesByTopicId(idCourse, idTopic)
          .subscribe((exercices) => {
            this.exerciceCount = exercices.length;
          });
      } else {
        console.warn('IdTopic ou IdCourse is not a number');
      }
    } else {
      console.log(
        'Pas de idTopic dans la route → probablement sur /topic/create'
      );
    }
  }

  onGetTopicById(idCourse: number, idTopic: number): void {
    this.topicService.getTopicById(idCourse, idTopic).subscribe({
      next: (topic) => {
        this.topic = topic; // Assuming the API returns a single topic
      },
      error: (error) => {
        console.error('Error fetching topic:', error);
      },
    });
  }

  onDeleteTopic(
    idCourse: number | undefined,
    idTopic: number | undefined
  ): void {
    if (idCourse !== undefined && idTopic !== undefined) {
      this.topicService.deleteTopic(idCourse, idTopic).subscribe({
        next: () => {
          this.router.navigate([`/course/${idCourse}`]);
        },
        error: (error) => {
          console.error('Error deleting topic:', error);
        },
      });
    }
  }

  onViewUpdateTopic(idCourse: number | undefined, idTopic: number | undefined) {
    if (idCourse !== undefined && idTopic !== undefined) {
      this.router.navigate([`/course/${idCourse}/topic/edit/${idTopic}`]);
    }
  }

  onViewCreateExercice() {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');
    if (idCourseParam !== null && idTopicParam !== null) {
      const idCourse = Number(idCourseParam);
      const idTopic = Number(idTopicParam);
      this.router.navigate([
        `/course/${idCourse}/topic/${idTopic}/exercices/create`,
      ]);
    } else {
      console.error('No course or topic ID found in route parameters.');
    }
  }
}
