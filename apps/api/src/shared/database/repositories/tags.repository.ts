import { Injectable } from '@nestjs/common';

import { Tag } from 'src/modules/tags/entities/tag.entity';

import {
  CreateTagDto,
  DeleteTagDto,
  FindAllTagsByUserIdDto,
  FindUniqueTagByIdDto,
  ITagsRepository,
  UpdateTagDto,
} from '../contracts/tags-repository.contract';
import { PrismaService } from '../prisma.service';

@Injectable()
export class TagsRepository implements ITagsRepository {
  constructor(private readonly prismaService: PrismaService) {}

  async findAllByUserId(
    findAllTagsByUserIdDto: FindAllTagsByUserIdDto,
  ): Promise<Tag[]> {
    const { userId } = findAllTagsByUserIdDto;

    const tags = await this.prismaService.tag.findMany({
      where: { userId },
    });

    return tags;
  }

  async findUniqueById(
    findUniqueByIdDto: FindUniqueTagByIdDto,
  ): Promise<Tag | null> {
    const { tagId } = findUniqueByIdDto;

    const findedTag = await this.prismaService.tag.findUnique({
      where: { id: tagId },
    });

    return findedTag;
  }

  async create(createTagDto: CreateTagDto): Promise<Tag> {
    const { data, userId } = createTagDto;
    const { name } = data;

    const newTag = await this.prismaService.tag.create({
      data: {
        name,
        user: {
          connect: {
            id: userId,
          },
        },
      },
    });

    return newTag;
  }

  async update(updateTagDto: UpdateTagDto): Promise<Tag> {
    const { data } = updateTagDto;
    const { id, name } = data;

    const updatedTag = await this.prismaService.tag.update({
      where: { id },
      data: { name },
    });

    return updatedTag;
  }

  async delete(deleteTagDto: DeleteTagDto): Promise<void> {
    const { tagId } = deleteTagDto;

    await this.prismaService.tag.delete({
      where: { id: tagId },
    });
  }
}
