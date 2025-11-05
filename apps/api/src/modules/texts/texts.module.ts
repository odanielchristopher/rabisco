import { Module } from '@nestjs/common';

import { ITextsService } from './contracts/text-service.contract';
import { IValidateTextOwnershipService } from './contracts/validate-text-ownership-service.contract';
import { TextsService } from './services/texts.service';
import { ValidateTextOwnershipService } from './services/validate-text-ownership.service';
import { TextsController } from './texts.controller';

@Module({
  controllers: [TextsController],
  providers: [
    { provide: ITextsService, useClass: TextsService },
    {
      provide: IValidateTextOwnershipService,
      useClass: ValidateTextOwnershipService,
    },
  ],
})
export class TextsModule {}
