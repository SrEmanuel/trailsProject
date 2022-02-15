import { Subject } from "./subject";

export interface Topic {
  id: number;
  name: string;
  position: number;
  subjects: Subject[];
}
