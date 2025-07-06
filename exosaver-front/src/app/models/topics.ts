export interface Topic {
  id?: number; // Optional, as it may not be set when creating a new topic
  title: string;
  description: string;
  courseId?: number; // The ID of the course this topic belongs to
}
