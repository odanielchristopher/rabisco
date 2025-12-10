import { Logger } from '@nestjs/common';

export class BaseListener {
  logger: Logger;

  constructor(name: string) {
    this.logger = new Logger(name);
  }
}
