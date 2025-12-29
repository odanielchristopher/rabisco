export const countWords = (value: string) => value.trim().split(/\s+/).length;

export function differenceInDays(a: Date, b: Date) {
  const start = new Date(a.getFullYear(), a.getMonth(), a.getDate());
  const end = new Date(b.getFullYear(), b.getMonth(), b.getDate());
  return Math.floor((start.getTime() - end.getTime()) / (1000 * 60 * 60 * 24));
}
