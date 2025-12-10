import { Global, Module } from '@nestjs/common';

import { ITextsRepository } from './contracts/texts-repository.contract';
import { IUsersRepository } from './contracts/users-repository.contract';
import { PrismaService } from './prisma.service';
import { TextsRepository } from './repositories/texts.repository';
import { UsersRepository } from './repositories/users.repository';

@Global()
@Module({
  providers: [
    PrismaService,
    {
      provide: IUsersRepository,
      useClass: UsersRepository,
    },
    {
      provide: ITextsRepository,
      useClass: TextsRepository,
    },
  ],
  exports: [
    {
      provide: IUsersRepository,
      useClass: UsersRepository,
    },
    {
      provide: ITextsRepository,
      useClass: TextsRepository,
    },
  ],
})
export class DatabaseModule {}
