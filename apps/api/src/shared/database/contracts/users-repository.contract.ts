import { User } from 'src/modules/users/entities/user.entity';

export const IUsersRepository = Symbol('IUsersRepository');

export interface IUsersRepository {
  findUniquetById(
    findUniqueByIdDto: FindUniqueUserByIdDto,
  ): Promise<User | null>;
  findUniqueByEmail(
    findUniqueByEmail: FindUniqueUserByEmailDto,
  ): Promise<User | null>;
  create(createUserDto: CreateUserDto): Promise<User>;
  update(updateUserDto: UpdateUserDto): Promise<User>;
  delete(deleteUserDto: DeleteUserDto): Promise<void>;
}

export type FindUniqueUserByIdDto = {
  userId: string;
};

export type FindUniqueUserByEmailDto = {
  email: string;
};

export type CreateUserDto = {
  data: User | Omit<User, 'id'>;
};

export type UpdateUserDto = {
  data: User;
};

export type DeleteUserDto = {
  userId: string;
};
