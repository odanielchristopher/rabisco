import { Global, Module } from '@nestjs/common';

import { IAchievementsRepository } from './contracts/achievements-repository.contract';
import { ITextsRepository } from './contracts/texts-repository.contract';
import { IUsersRepository } from './contracts/users-repository.contract';
import { PrismaService } from './prisma.service';
import { AchievementsRepository } from './repositories/achievements.repository';
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
    {
      provide: IAchievementsRepository,
      useClass: AchievementsRepository,
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
    {
      provide: IAchievementsRepository,
      useClass: AchievementsRepository,
    },
  ],
})
export class DatabaseModule {}
