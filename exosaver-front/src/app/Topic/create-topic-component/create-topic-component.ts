import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TopicService } from '../../services/topic-service';
import { Topic } from './../../models/topics';
import { Component, OnInit } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';

@Component({
  selector: 'app-create-topic-component',
  imports: [FormsModule],
  templateUrl: './create-topic-component.html',
  styleUrl: './create-topic-component.scss',
})
export class CreateTopicComponent {
  topic: Topic = {
    title: '',
    description: '',
  };
  topics: Topic[] = [];

  constructor(
    private topicService: TopicService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  onSubmit(form: NgForm) {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    if (idCourseParam !== null) {
      const idCourse = Number(idCourseParam);
      this.topicService.createTopic(idCourse, this.topic).subscribe({
        next: (createdTopic) => {
          this.topics.push(createdTopic);
          form.resetForm();
          this.router.navigate([`/course/${idCourse}`]);
        },
        error: (error) => {
          console.error('Error creating topic:', error);
        },
      });
    }
  }

  onReturnCourseView() {
    const idCourseParam = this.route.snapshot.paramMap.get('idCourse');
    if (idCourseParam !== null) {
      const idCourse = Number(idCourseParam);
      this.router.navigate([`/course/${idCourse}`]);
    } else {
      console.error('No course ID found in route parameters.');
    }
  }
}
