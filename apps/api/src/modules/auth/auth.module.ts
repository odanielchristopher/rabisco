import { Module } from '@nestjs/common';
import { JwtModule } from '@nestjs/jwt';

import { env } from '@shared/config/env';

import { AuthController } from './auth.controller';
import { AuthService } from './auth.service';
import { IAuthService } from './contracts/auth-service.contract';

@Module({
  imports: [
    JwtModule.register({
      global: true,
      signOptions: { expiresIn: '7d' },
      secret: env.jwtSecret,
    }),
  ],
  controllers: [AuthController],
  providers: [{ provide: IAuthService, useClass: AuthService }],
})
export class AuthModule {}
