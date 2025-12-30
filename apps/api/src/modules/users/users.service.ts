import {
  ConflictException,
  Injectable,
  NotFoundException,
  UnauthorizedException,
} from '@nestjs/common';
import { compare, hash } from 'bcryptjs';

import { IUsersRepository } from 'src/shared/database/contracts/users-repository.contract';
import { PrismaService } from 'src/shared/database/prisma.service';

import { IUsersService } from './contracts/users-service.contract';
import { UpdateUserDto } from './dto/update-user.dto';

@Injectable()
export class UsersService implements IUsersService {
  constructor(
    private readonly usersRepository: IUsersRepository,
    private readonly prismaService: PrismaService,
  ) {}

  async getUserById(userId: string) {
    const user = await this.usersRepository.findUniquetById({
      userId,
    });

    if (!user) {
      throw new NotFoundException('Usuário não encontrado.');
    }

    const now = new Date();
    const sevenDaysAgo = new Date(now.getTime());
    sevenDaysAgo.setDate(sevenDaysAgo.getDate() - 7);

    const [totalTexts, textsThisWeek, wordsAggregate, userScore, streak] =
      await Promise.all([
        this.prismaService.text.count({
          where: { userId },
        }),
        this.prismaService.text.count({
          where: {
            userId,
            createdAt: {
              gte: sevenDaysAgo,
              lte: now,
            },
          },
        }),
        this.prismaService.text.aggregate({
          _sum: { wordCount: true },
          where: { userId },
        }),
        this.prismaService.userScore.findUnique({
          where: { userId },
        }),
        this.prismaService.streak.findUnique({
          where: { userId },
        }),
      ]);

    const totalWords = wordsAggregate._sum.wordCount ?? 0;
    const totalDays = streak?.daySequence ?? 0;
    const score = userScore?.points ?? 0;

    return {
      name: user.name,
      email: user.email,
      totalDays,
      totalTexts,
      textsThisWeek,
      totalWords,
      score,
    };
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
