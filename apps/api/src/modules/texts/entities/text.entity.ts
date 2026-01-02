import { TextType } from './text-type.enum';

export class Text {
  id: string;
  type: TextType;
  title: string;
  content: string;
  wordCount: number;
  createdAt: Date;
  updatedAt: Date;
  dailyPromptId?: string | null;
}
