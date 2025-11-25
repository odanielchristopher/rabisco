import { Global, Module } from '@nestjs/common';

import { IUsersRepository } from './contracts/users-repository.contract';
import { ITagsRepository } from './contracts/tags-repository.contract';
import { PrismaService } from './prisma.service';
import { UsersRepository } from './repositories/users.repository';
import { TagsRepository } from './repositories/tags.repository';

@Global()
@Module({
  providers: [
    PrismaService,
    {
      provide: IUsersRepository,
      useClass: UsersRepository,
    },
    {
      provide: ITagsRepository,
      useClass: TagsRepository,
    },
  ],
  exports: [
    {
      provide: IUsersRepository,
      useClass: UsersRepository,
    },
    {
      provide: ITagsRepository,
      useClass: TagsRepository,
    },
  ],
})
export class DatabaseModule {}
