import { CardFull } from '../../shared/card-full/card-full';
import { Topic } from './../../models/topics';
import { TopicService } from './../../services/topic-service';
import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-topics-of-one-course-component',
  imports: [CardFull, MatButtonModule],
  templateUrl: './topics-of-one-course-component.html',
  styleUrl: './topics-of-one-course-component.scss',
})
export class TopicsOfOneCourseComponent implements OnInit {
  topic: Topic[] = [];
  OneTopic: Topic = {
    title: '',
    description: '',
  };

  constructor(
    private topicService: TopicService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');

    if (idCourseParam !== null) {
      const idCourse = Number(idCourseParam);
      this.topicService.getAllTopicsByCourseId(idCourse).subscribe((topics) => {
        this.topic = topics;
      });
    }
  }

  onViewOneTopic(
    idCourse: number | undefined,
    idTopic: number | undefined
  ): void {
    this.router.navigate([`course/${idCourse}/topic/${idTopic}`]);
  }

  onCardButtonClick(
    idCourse: number | undefined,
    idTopic: number | undefined
  ): () => void {
    return () => this.onViewOneTopic(idCourse, idTopic);
  }

  onViewCreateTopic(): void {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    if (idCourseParam !== null) {
      const idCourse = Number(idCourseParam);
      this.router.navigate([`course/${idCourse}/topic/create`]);
    }
  }
}
