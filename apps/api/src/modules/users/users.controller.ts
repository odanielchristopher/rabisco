import {
  Body,
  Controller,
  Delete,
  Get,
  HttpCode,
  HttpStatus,
  Put,
} from '@nestjs/common';

import { ActiveUserId } from '@shared/decorators/active-user-id.decorator';

import { IUsersService } from './contracts/users-service.contract';
import { UpdateUserDto } from './dto/update-user.dto';

@Controller('users')
export class UsersController {
  constructor(private readonly usersService: IUsersService) {}

  @Get('/me')
  me(@ActiveUserId() userId: string) {
    return this.usersService.getUserById(userId);
  }

  @Put('/edit-me')
  update(@ActiveUserId() userId: string, @Body() updateUserDto: UpdateUserDto) {
    return this.usersService.update(userId, updateUserDto);
  }

  @Delete('/delete-me')
  @HttpCode(HttpStatus.NO_CONTENT)
  remove(@ActiveUserId() userId: string) {
    return this.usersService.remove(userId);
  }
}
