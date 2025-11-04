import { ApiProperty } from '@nestjs/swagger';
import {
  IsEmail,
  IsNotEmpty,
  IsOptional,
  IsString,
  MinLength,
} from 'class-validator';

export class UpdateUserDto {
  @IsString()
  @IsNotEmpty()
  @ApiProperty({
    example: 'John Doe',
    description: 'Nome do usuário para atualização.',
  })
  name: string;

  @IsString()
  @IsNotEmpty({ message: 'O e-mail é obrigatório.' })
  @IsEmail()
  @ApiProperty({
    example: 'johndoe@example.com',
    description: 'E-mail do usuário para atualização.',
  })
  email: string;

  @IsString({ message: 'A senha precisa ser uma string.' })
  @IsNotEmpty({ message: 'A senha é obrigatória.' })
  @MinLength(6, { message: 'A senha precisa ter pelo menos 6 caracteres.' })
  @ApiProperty({
    example: 'oldpassword123',
    description: 'Senha atual do usuário.',
  })
  currentPassword: string;

  @IsString({ message: 'A senha precisa ser uma string.' })
  @IsOptional()
  @MinLength(6, { message: 'A senha precisa ter pelo menos 6 caracteres.' })
  @ApiProperty({
    example: 'newpassword456',
    description: 'Nova senha do usuário, caso desejada.',
  })
  newPassword: string;
}
