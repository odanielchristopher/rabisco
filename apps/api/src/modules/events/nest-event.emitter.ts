import { Injectable } from '@nestjs/common';
import { EventEmitter2 } from '@nestjs/event-emitter';

import { CustomEventEmitter } from './contract/custom-event-emitter.contract';

@Injectable()
export class NestEventEmitter implements CustomEventEmitter {
  constructor(private readonly nestEventEmitter: EventEmitter2) {}

  emit(event: string, ...values: any[]): boolean {
    // eslint-disable-next-line @typescript-eslint/no-unsafe-argument
    return this.nestEventEmitter.emit(event, ...values);
  }
}
