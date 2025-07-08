import { FeynmanStatus } from './feynmanStatus';

export interface exercices {
  id?: number;
  title: string;
  description: string;
  solution?: string;
  feynmanStatus?: FeynmanStatus; // Optional, can be used to track the Feynman technique status
  nextReviewDate?: Date; // Optional, to track when the exercise should be reviewed next
  feynmanSuccessCount?: number; // Optional, to track when the exercise was created
  topicId?: number;
}
