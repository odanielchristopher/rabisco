import { User } from 'src/modules/users/entities/user.entity';

export abstract class IUsersRepository {
  abstract findUniquetById(
    findUniqueByIdDto: FindUniqueUserByIdDto,
  ): Promise<User | null>;
  abstract findUniqueByEmail(
    findUniqueByEmail: FindUniqueUserByEmailDto,
  ): Promise<User | null>;
  abstract create(createUserDto: CreateUserDto): Promise<User>;
  abstract update(updateUserDto: UpdateUserDto): Promise<User>;
  abstract delete(deleteUserDto: DeleteUserDto): Promise<void>;
}

export type FindUniqueUserByIdDto = {
  userId: string;
};

export type FindUniqueUserByEmailDto = {
  email: string;
};

export type CreateUserDto = {
  data: User | Omit<User, 'id'>;
  relations?: {
    categories: { name: string }[];
  };
};

export type UpdateUserDto = {
  data: User;
};

export type DeleteUserDto = {
  userId: string;
};
