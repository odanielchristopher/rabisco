import { Text } from '@modules/texts/entities/text.entity';
import { BaseDto } from '@shared/types/utils.type';

export interface ITextsRepository {
  findAllByUserId(findAllDto: FindAllTextsDto): Promise<Text[]>;

  findOneById(findOneDto: FindOneTextDto): Promise<Text | null>;

  create(createTextDto: CreateTextDto): Promise<Text>;

  update(updateTextDto: UpdateTextDto): Promise<Text>;

  delete(deleteTextDto: DeleteTextDto): Promise<void>;
}

export type FindAllTextsDto = BaseDto & {
  filters?: {
    category?: string;
    searchQuery?: string;
  };
};

export type FindOneTextDto = BaseDto;

export type CreateTextDto = BaseDto & { data: Omit<Text, 'id'> };

export type UpdateTextDto = BaseDto & {
  textId: string;
  data: Omit<Text, 'id'>;
};

export type DeleteTextDto = BaseDto & {
  textId: string;
  data: Omit<Text, 'id'>;
};
