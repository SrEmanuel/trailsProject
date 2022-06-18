import { ISubject } from "../interfaces/subject"

export const reorderSubjects = (firstSubject: ISubject , secondSubject: ISubject ) => {
  if (firstSubject.position < secondSubject.position) {
    return -1
  }

  if (firstSubject.position > secondSubject.position) {
    return 1
  }

  return 0
}