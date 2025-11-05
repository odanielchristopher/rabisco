import { Text } from '@modules/texts/entities/text.entity';
import { BaseDto } from '@shared/types/utils.type';

export const ITextsRepository = Symbol('ITextsRepository');

export interface ITextsRepository {
  findAllByUserId(
    findAllDto: ITextsRepository.FindAllTextsDto,
  ): Promise<Text[]>;

  findOneById(
    findOneDto: ITextsRepository.FindOneTextDto,
  ): Promise<Text | null>;

  create(createTextDto: ITextsRepository.CreateTextDto): Promise<Text>;

  update(updateTextDto: ITextsRepository.UpdateTextDto): Promise<Text>;

  delete(deleteTextDto: ITextsRepository.DeleteTextDto): Promise<void>;
}

export namespace ITextsRepository {
  export type FindAllTextsDto = BaseDto & {
    filters?: {
      category?: string;
      searchQuery?: string;
    };
  };

  export type FindOneTextDto = BaseDto & { textId: string };

  export type CreateTextDto = BaseDto & {
    data: Omit<Text, 'id' | 'createdAt' | 'updatedAt'>;
  };

  export type UpdateTextDto = {
    textId: string;
    data: Omit<Text, 'id' | 'createdAt' | 'updatedAt'>;
  };

  export type DeleteTextDto = {
    textId: string;
  };
}
