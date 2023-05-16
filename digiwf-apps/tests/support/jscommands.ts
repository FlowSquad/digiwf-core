export const simpleArrayCompare = (a: Array<string|number>, b: Array<string|number>): boolean => {
  return Array.isArray(a) &&
    Array.isArray(b) &&
    a.length === b.length &&
    a.every((val: string|number, index: number): boolean => val === b[index]);
}
