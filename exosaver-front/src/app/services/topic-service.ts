import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from '../models/topics'; // Assuming you have a model for topics

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  private apiUrl = 'http://localhost:8080/api/course';

  constructor(private http: HttpClient) {}

  getAllTopicsByCourseId(idCourse: number): Observable<Topic[]> {
    const url = `${this.apiUrl}/${idCourse}/topics`;
    return this.http.get<Topic[]>(url);
  }

  createTopic(idCourse: number, topic: Topic): Observable<Topic> {
    const url = `${this.apiUrl}/${idCourse}/topics`;
    return this.http.post<Topic>(url, topic);
  }

  deleteTopic(idCourse: number, idTopic: number): Observable<void> {
    const url = `${this.apiUrl}/${idCourse}/topic/${idTopic}`;
    return this.http.delete<void>(url);
  }

  updateTopic(
    idCourse: number,
    idTopic: number,
    topic: Topic
  ): Observable<Topic> {
    const url = `${this.apiUrl}/${idCourse}/topic/${idTopic}`;
    return this.http.put<Topic>(url, topic);
  }

  getTopicById(idCourse: number, idTopic: number): Observable<Topic> {
    const url = `${this.apiUrl}/${idCourse}/topic/${idTopic}`;
    return this.http.get<Topic>(url);
  }
}
