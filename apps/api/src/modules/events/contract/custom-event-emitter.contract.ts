export abstract class CustomEventEmitter {
  abstract emit(event: string, ...values: any[]): boolean;
}
