import { ApiProperty } from '@nestjs/swagger';
import { IsEmail, IsNotEmpty, IsString, MinLength } from 'class-validator';

export class SignupDto {
  @IsString()
  @IsNotEmpty()
  @ApiProperty({
    example: 'João Silva',
    description: 'Nome completo do usuário para cadastro.',
  })
  name: string;

  @IsString()
  @IsNotEmpty({ message: 'O e-mail é obrigatório.' })
  @IsEmail()
  @ApiProperty({
    example: 'joao.silva@exemplo.com',
    description: 'E-mail do usuário para cadastro. Deve ser um e-mail válido.',
  })
  email: string;

  @IsString({ message: 'A senha precisa ser uma string.' })
  @IsNotEmpty({ message: 'A senha é obrigatória.' })
  @MinLength(6, { message: 'A senha precisa ter pelo menos 6 caracteres.' })
  @ApiProperty({
    example: 'senhaForte123',
    description:
      'Senha do usuário para cadastro. Deve ter no mínimo 6 caracteres.',
  })
  password: string;
}
