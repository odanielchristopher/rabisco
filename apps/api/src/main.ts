import 'dotenv/config';

import { Logger, ValidationPipe } from '@nestjs/common';
import { NestFactory } from '@nestjs/core';
import { DocumentBuilder, SwaggerModule } from '@nestjs/swagger';
import { apiReference } from '@scalar/nestjs-api-reference';
import type { Request, Response } from 'express';

import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);

  app.useGlobalPipes(
    new ValidationPipe({
      transform: true,
    }),
  );
  app.useGlobalGuards();
  app.enableCors();

  const config = new DocumentBuilder()
    .setTitle('Rabisco API')
    .setDescription('Documentação da API')
    .setVersion('1.0')
    .addBearerAuth()
    .build();

  const document = SwaggerModule.createDocument(app, config);

  const httpAdapter = app.getHttpAdapter();
  httpAdapter.get('/openapi.json', (_req: Request, res: Response) => {
    res.json(document);
  });

  app.use(
    '/docs',
    apiReference({
      theme: 'kepler',
      content: document,
      persistAuth: true,
    }),
  );

  const port = process.env.PORT ?? 3001;

  await app.listen(port, '0.0.0.0');

  const logger = new Logger('Bootstrap');
  logger.log(`Application is running on: http://localhost:${port}`);
  logger.log(`API docs available at: http://localhost:${port}/docs`);
  logger.log(
    `OpenAPI JSON available at: http://localhost:${port}/openapi.json`,
  );
}
bootstrap();
