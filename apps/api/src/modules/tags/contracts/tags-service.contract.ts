import { CreateTagDto } from '../dto/create-tag.dto';
import { Tag } from '../entities/tag.entity';

export const ITagsService = Symbol('ITagsService');

export interface ITagsService {
  getTagById(userId: string): Promise<Partial<Tag> | null>;
  remove(userId: string, tagId: string): Promise<void>;
}
