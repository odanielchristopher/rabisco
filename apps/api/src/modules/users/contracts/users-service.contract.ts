import { UpdateUserDto } from '../dto/update-user.dto';
import { User } from '../entities/user.entity';

export const IUsersService = Symbol('IUsersService');

export interface IUsersService {
  getUserById(userId: string): Promise<Partial<User> | null>;

  update(
    userId: string,
    updateUserDto: UpdateUserDto,
  ): Promise<Partial<User> | null>;

  remove(userId: string): Promise<void>;
}
