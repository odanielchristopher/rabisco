import { UpdateUserDto } from '../dto/update-user.dto';
import { User } from '../entities/user.entity';

export abstract class IUsersService {
  abstract getUserById(userId: string): Promise<Partial<User> | null>;

  abstract update(
    userId: string,
    updateUserDto: UpdateUserDto,
  ): Promise<Partial<User> | null>;

  abstract remove(userId: string): Promise<void>;
}
