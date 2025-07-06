import { TopicService } from './../../services/topic-service';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Topic } from '../../models/topics';

@Component({
  selector: 'app-edit-topic-component',
  imports: [FormsModule, RouterLink],
  templateUrl: './edit-topic-component.html',
  styleUrl: './edit-topic-component.scss',
})
export class EditTopicComponent implements OnInit {
  topic: Topic = {
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
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');

    if (idCourseParam && idTopicParam) {
      const idCourse = Number(idCourseParam);
      const idTopic = Number(idTopicParam);
      this.topicService.getTopicById(idCourse, idTopic).subscribe({
        next: (topic) => {
          this.topic = topic;
        },
      });
    } else {
      console.error(
        'Course ID or Topic ID is missing in the route parameters.'
      );
      return;
    }
  }

  onUpdateTopic(form: NgForm) {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    const idTopicParam = this.route.snapshot.paramMap.get('idTopic');

    if (idCourseParam && idTopicParam) {
      const idCourse = Number(idCourseParam);
      const idTopic = Number(idTopicParam);

      this.topicService.updateTopic(idTopic, idCourse, this.topic).subscribe({
        next: () => {
          this.router.navigate([`/course/${idCourse}/topics/${idTopic}`]);
        },
        error: (error) => {
          console.error('Error updating topic:', error);
        },
      });
    }
  }
}
