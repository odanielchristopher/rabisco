import { Module } from '@nestjs/common';
import { ConfigModule } from '@nestjs/config';
import { APP_GUARD } from '@nestjs/core';

import { AuthGuard } from '@modules/auth/auth.guard';
import { AuthModule } from '@modules/auth/auth.module';
import { TextsModule } from '@modules/texts/texts.module';
import { UsersModule } from '@modules/users/users.module';
import { DatabaseModule } from '@shared/database/database.module';

@Module({
  imports: [
    ConfigModule.forRoot({ isGlobal: true }),
    UsersModule,
    DatabaseModule,
    AuthModule,
    TextsModule,
  ],
  providers: [
    {
      provide: APP_GUARD,
      useClass: AuthGuard,
    },
  ],
})
export class AppModule {}
