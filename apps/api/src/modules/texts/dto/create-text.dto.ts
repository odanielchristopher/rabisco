import { ApiProperty } from '@nestjs/swagger';
import {
  IsArray,
  IsEnum,
  IsNotEmpty,
  IsOptional,
  IsString,
  IsUUID,
} from 'class-validator';

import { TextType } from '../entities/text-type.enum';

export class CreateTextDto {
  @IsEnum(TextType)
  @IsNotEmpty()
  @ApiProperty({
    example: [...Object.values(TextType)],
    description: 'Tipo de texto.',
  })
  type: TextType;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({
    example: 'Texto de hoje',
    description: 'Título do texto.',
  })
  title: string;

  @IsString()
  @IsNotEmpty()
  @ApiProperty({
    description: 'Conteúdo do texto.',
  })
  content: string;

  @IsOptional()
  @IsArray()
  @IsUUID('4', { each: true })
  @ApiProperty({
    example: ['b4e8ed1b-95c8-4bff-b28c-e4fd14ac4b9c'],
    description: 'Lista de IDs de categorias do texto.',
  })
  categoryIds?: string[];
}
