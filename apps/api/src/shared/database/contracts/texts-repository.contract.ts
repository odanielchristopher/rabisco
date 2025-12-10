import { Text } from '@modules/texts/entities/text.entity';
import { BaseDto } from '@shared/types/utils.type';

export abstract class ITextsRepository {
  abstract findAllByUserId(
    findAllDto: ITextsRepository.FindAllTextsDto,
  ): Promise<Text[]>;

  abstract findOneById(
    findOneDto: ITextsRepository.FindOneTextDto,
  ): Promise<Text | null>;

  abstract create(createTextDto: ITextsRepository.CreateTextDto): Promise<Text>;

  abstract update(updateTextDto: ITextsRepository.UpdateTextDto): Promise<Text>;

  abstract delete(deleteTextDto: ITextsRepository.DeleteTextDto): Promise<void>;
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
