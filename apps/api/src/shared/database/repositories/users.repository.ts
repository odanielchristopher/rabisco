import { Injectable } from '@nestjs/common';

import { User } from 'src/modules/users/entities/user.entity';

import {
  CreateUserDto,
  DeleteUserDto,
  FindUniqueUserByEmailDto,
  FindUniqueUserByIdDto,
  IUsersRepository,
  UpdateUserDto,
} from '../contracts/users-repository.contract';
import { PrismaService } from '../prisma.service';

@Injectable()
export class UsersRepository implements IUsersRepository {
  constructor(private readonly prismaService: PrismaService) {}

  async findUniquetById(
    findUniqueByIdDto: FindUniqueUserByIdDto,
  ): Promise<User | null> {
    const { userId } = findUniqueByIdDto;

    const findedUser = await this.prismaService.user.findUnique({
      where: { id: userId },
    });

    return findedUser as User;
  }

  async findUniqueByEmail(
    findUniqueByEmail: FindUniqueUserByEmailDto,
  ): Promise<User | null> {
    const { email } = findUniqueByEmail;

    const findedUser = await this.prismaService.user.findUnique({
      where: { email },
    });

    return findedUser as User;
  }

  async create(createUserDto: CreateUserDto): Promise<User> {
    const { data, relations } = createUserDto;

    const categories = relations?.categories ?? [];
    const tags = relations?.tags ?? [];

    const newUser = await this.prismaService.user.create({
      data: {
        ...data,
        ...(categories.length && {
          categories: {
            createMany: {
              data: categories,
            },
          },
        }),
        ...(tags.length && {
          tag: {
            createMany: {
              data: tags,
            },
          },
        }),
        score: {
          create: {
            points: 0,
          },
        },
        streak: {
          create: {
            daySequence: 0,
          },
        },
      },
      select: {
        id: true,
        name: true,
        email: true,
        password: true,
      },
    });

    return newUser;
  }

  async update(updateUserDto: UpdateUserDto): Promise<User> {
    const { data } = updateUserDto;

    const updatedUser = await this.prismaService.user.update({
      where: { id: data.id },
      data,
      select: {
        id: true,
        name: true,
        avatarPath: true,
        email: true,
        password: true,
      },
    });

    return updatedUser as User;
  }

  async delete(deleteUserDto: DeleteUserDto): Promise<void> {
    const { userId } = deleteUserDto;

    await this.prismaService.user.delete({
      where: { id: userId },
    });
  }
}
