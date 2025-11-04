import { ApiProperty } from '@nestjs/swagger';
import { IsEmail, IsNotEmpty, IsString, MinLength } from 'class-validator';

export class SigninDto {
  @IsString()
  @IsNotEmpty({ message: 'O e-mail é obrigatório.' })
  @IsEmail()
  @ApiProperty({
    example: 'usuario@exemplo.com',
    description: 'E-mail do usuário para autenticação.',
  })
  email: string;

  @IsString({ message: 'A senha precisa ser uma string.' })
  @IsNotEmpty({ message: 'A senha é obrigatória.' })
  @MinLength(6, { message: 'A senha precisa ter pelo menos 6 caracteres.' })
  @ApiProperty({
    example: 'senhaSegura123',
    description:
      'Senha do usuário para autenticação. Deve ter no mínimo 6 caracteres.',
  })
  password: string;
}
