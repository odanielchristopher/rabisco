import { Inject, Injectable, NotFoundException } from '@nestjs/common';
import { CreateTagDto } from './dto/create-tag.dto';
import { Tag } from './entities/tag.entity';
import { ITagsRepository } from 'src/shared/database/contracts/tags-repository.contract';
import { ITagsService } from './contracts/tags-service.contract';

@Injectable()
export class TagsService implements ITagsService {
  constructor(
    @Inject(ITagsRepository)
    private readonly tagsRepository: ITagsRepository,
  ) {}

  async create(createTagDto: CreateTagDto, userId: string) {
    const { name } = createTagDto;

    const data = { data: { name } };

    return this.tagsRepository.create(data, userId);
  }

  async findAll(userId: string) {
    return this.tagsRepository.findAllByUserId({ userId });
  }

  async getTagById(userId: string, tagId: string) {
    const tag = await this.tagsRepository.findUniqueById({ tagId }, userId);

    if (!tag) {
      throw new NotFoundException('Tag não encontrada.');
    }

    return tag;
  }

  async update(userId: string, tagId: string, updateTagDto: Partial<Tag>) {
    const tag = await this.tagsRepository.findUniqueById({ tagId }, userId);

    if (!tag) {
      throw new NotFoundException('Tag não encontrada.');
    }

    const data = { data: { id: tagId, name: updateTagDto.name ?? tag.name } };

    return this.tagsRepository.update(data);
  }

  async remove(userId: string, tagId: string) {
    const tag = await this.tagsRepository.findUniqueById({ tagId }, userId);

    if (!tag) {
      throw new NotFoundException('Tag não encontrada.');
    }
    await this.tagsRepository.delete({ tagId });
  }
}
