import { Text } from '../entities/text.entity';

export abstract class IValidateTextOwnershipService {
  abstract validate(input: IValidateTextOwnershipService.Input): Promise<Text>;
}

export namespace IValidateTextOwnershipService {
  export type Input = {
    textId: string;
    userId: string;
  };
}
