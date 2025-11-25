import { Tag } from 'src/modules/tags/entities/tag.entity';

export const ITagsRepository = Symbol('ITagsRepository');

export interface ITagsRepository {
  findAllByUserId(
    findAllTagsByUserIdDto: FindAllTagsByUserIdDto,
  ): Promise<Tag[]>;
  findUniqueById(findUniqueByIdDto: FindUniqueTagByIdDto): Promise<Tag | null>;
  create(createTagDto: CreateTagDto): Promise<Tag>;
  update(updateTagDto: UpdateTagDto): Promise<Tag>;
  delete(deleteTagDto: DeleteTagDto): Promise<void>;
}

export type FindAllTagsByUserIdDto = {
  userId: string;
};

export type FindUniqueTagByIdDto = {
  tagId: string;
};

export type CreateTagDto = {
  data: Tag | Omit<Tag, 'id'>;
  userId: string;
};

export type UpdateTagDto = {
  data: Tag;
};

export type DeleteTagDto = {
  tagId: string;
};
