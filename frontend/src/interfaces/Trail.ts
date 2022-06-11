import { IUser } from "./user";

export interface ITrails {
  id: number;
  name: string;
  imagePath: string;
  subjectsCount: number;
  linkName: string;
  completedCount: number;
  professors: IUser[];
}