import { Global, Module } from '@nestjs/common';

import { IUsersRepository } from './contracts/users-repository.contract';
import { PrismaService } from './prisma.service';
import { UsersRepository } from './repositories/users.repository';

@Global()
@Module({
  providers: [
    PrismaService,
    {
      provide: IUsersRepository,
      useClass: UsersRepository,
    },
  ],
  exports: [
    {
      provide: IUsersRepository,
      useClass: UsersRepository,
    },
  ],
})
export class DatabaseModule {}
