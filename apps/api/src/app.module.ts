import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { APP_GUARD } from '@nestjs/core';
import { ScheduleModule } from '@nestjs/schedule';

import { AuthGuard } from '@modules/auth/auth.guard';
import { AuthModule } from '@modules/auth/auth.module';
import { EventsModule } from '@modules/events/events.module';
import { GamificationModule } from '@modules/gamification/gamification.module';
import { PromptsModule } from '@modules/prompts/prompts.module';
import { TagsModule } from '@modules/tags/tags.module';
import { TextsModule } from '@modules/texts/texts.module';
import { UsersModule } from '@modules/users/users.module';
import { DatabaseModule } from '@shared/database/database.module';

@Module({
  imports: [
    ConfigModule.forRoot({ isGlobal: true }),
    ScheduleModule.forRoot(),
    EventsModule,
    UsersModule,
    DatabaseModule,
    AuthModule,
    TextsModule,
    GamificationModule,
    TagsModule,
    PromptsModule,
  ],
  providers: [
    {
      provide: APP_GUARD,
      useClass: AuthGuard,
    },
  ],
})
export class AppModule {}
