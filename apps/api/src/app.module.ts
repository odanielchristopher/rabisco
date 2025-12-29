import { Module } from '@nestjs/common';
import { APP_GUARD } from '@nestjs/core';

import { AuthGuard } from '@modules/auth/auth.guard';
import { AuthModule } from '@modules/auth/auth.module';
import { UsersModule } from '@modules/users/users.module';
import { DatabaseModule } from '@shared/database/database.module';
import { TagsModule } from './modules/tags/tags.module';

@Module({
  imports: [UsersModule, DatabaseModule, AuthModule, TagsModule],
  providers: [
    {
      provide: APP_GUARD,
      useClass: AuthGuard,
    },
  ],
})
export class AppModule {}
