import { Global, Module } from '@nestjs/common';

import { IAchievementsRepository } from './contracts/achievements-repository.contract';
import { IMissionsRepository } from './contracts/missions-repository.contract';
import { IScoreRepository } from './contracts/score-repository.contract';
import { IStreakRepository } from './contracts/streak-repository.contract';
import { ITagsRepository } from './contracts/tags-repository.contract';
import { ITextsRepository } from './contracts/texts-repository.contract';
import { IUsersRepository } from './contracts/users-repository.contract';
import { PrismaService } from './prisma.service';
import { AchievementsRepository } from './repositories/achievements.repository';
import { MissionsRepository } from './repositories/missions.repository';
import { ScoreRepository } from './repositories/score.repository';
import { StreakRepository } from './repositories/streak.repository';
import { TagsRepository } from './repositories/tags.repository';
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
    {
      provide: IStreakRepository,
      useClass: StreakRepository,
    },
    {
      provide: IMissionsRepository,
      useClass: MissionsRepository,
    },
    {
      provide: IScoreRepository,
      useClass: ScoreRepository,
    },
    {
      useClass: TagsRepository,
      provide: ITagsRepository,
    },
  ],
  exports: [
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
    {
      provide: IStreakRepository,
      useClass: StreakRepository,
    },
    {
      provide: IMissionsRepository,
      useClass: MissionsRepository,
    },
    {
      provide: IScoreRepository,
      useClass: ScoreRepository,
    },
    {
      provide: ITagsRepository,
      useClass: TagsRepository,
    },
  ],
})
export class DatabaseModule {}
