import { CreateTagDto } from '../dto/create-tag.dto';
import { Tag } from '../entities/tag.entity';

export const ITagsService = Symbol('ITagsService');

export interface ITagsService {
  create(createTagDto: CreateTagDto, userId: string): Promise<Tag>;
  findAll(userId: string): Promise<Partial<Tag>[]>;
  getTagById(userId: string, tagId: string): Promise<Partial<Tag> | null>;
  update(
    userId: string,
    tagId: string,
    updateTagDto: Partial<Tag>,
  ): Promise<Tag>;
  remove(userId: string, tagId: string): Promise<void>;
}
