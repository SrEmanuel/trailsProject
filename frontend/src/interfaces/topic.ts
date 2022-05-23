import { ISubject } from "./subject";

export interface ITopic {
  id: number;
  name: string;
  linkName: string;
  position: number;
  subjects: ISubject[];
}
