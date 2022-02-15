import { ISubject } from "./subject";

export interface ITopic {
  id: number;
  name: string;
  position: number;
  subjects: ISubject[];
}
