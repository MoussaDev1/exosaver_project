import { TopicService } from './../../services/topic-service';
import { Component, Input, OnInit } from '@angular/core';
import { CourseService } from '../../services/course-service';
import { Topic } from '../../models/topics';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-topics-of-one-course-component',
  imports: [],
  templateUrl: './topics-of-one-course-component.html',
  styleUrl: './topics-of-one-course-component.scss',
})
export class TopicsOfOneCourseComponent implements OnInit {
  topic: Topic[] = [];

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
    this.router.navigate([`course/${idCourse}/topics/${idTopic}`]);
  }

  onViewCreateTopic(idCourse: number, topic: Topic): void {
    this.topicService.createTopic(idCourse, topic).subscribe((newTopic) => {
      this.topic.push(newTopic);
    });
  }
}
