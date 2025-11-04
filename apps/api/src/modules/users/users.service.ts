import {
  ConflictException,
  Inject,
  Injectable,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { compare, hash } from 'bcryptjs';

import { IUsersRepository } from 'src/shared/database/contracts/users-repository.contract';

import { IUsersService } from './contracts/users-service.contract';
import { UpdateUserDto } from './dto/update-user.dto';

@Injectable()
export class UsersService implements IUsersService {
  constructor(
    @Inject(IUsersRepository)
    private readonly usersRepository: IUsersRepository,
  ) {}

  async getUserById(userId: string) {
    const user = await this.usersRepository.findUniquetById({
      userId,
    });

    if (!user) {
      throw new NotFoundException('Usuário não encontrado.');
    }

    return { email: user.email, name: user.name };
  }

  async update(userId: string, updateUserDto: UpdateUserDto) {
    const user = await this.usersRepository.findUniquetById({
      userId,
    });

    if (!user) {
      throw new NotFoundException('Usuário não encontrado.');
    }

    const isPasswordValid = await compare(
      updateUserDto.currentPassword,
      user.password,
    );

    if (!isPasswordValid) {
      throw new UnauthorizedException('Senha inválida.');
    }

    if (user.email !== updateUserDto.email) {
      const emailAlredyInUse = await this.usersRepository.findUniqueByEmail({
        email: updateUserDto.email,
      });

      if (emailAlredyInUse) {
        throw new ConflictException('Esse e-mail já está em uso.');
      }
    }

    let hashedPassword = user.password;

    if (updateUserDto.newPassword) {
      hashedPassword = await hash(updateUserDto.newPassword, 10);
    }

    const { name, email } = await this.usersRepository.update({
      data: {
        id: userId,
        name: updateUserDto.name,
        email: updateUserDto.email,
        password: hashedPassword,
      },
    });

    return { name, email };
  }

  async remove(userId: string) {
    const user = await this.usersRepository.findUniquetById({
      userId,
    });

    if (!user) {
      throw new NotFoundException('Usuário não encontrado.');
    }

    await this.usersRepository.delete({ userId });
  }
}
