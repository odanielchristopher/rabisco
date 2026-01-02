import { ApiProperty } from '@nestjs/swagger';
import { IsNotEmpty, IsString, Min, MinLength } from 'class-validator';

export class UpdateTagDto {
  @IsString()
  @IsNotEmpty()
  @MinLength(3, {
    message: 'O nome da tag precisa ter pelo menos 3 caracteres.',
  })
  @ApiProperty({
    example: 'Romance',
    description: 'Nome da tag a ser criada.',
  })
  name: string;
}
