import { IQuestion } from "./question";

export interface ISubject{
    position: number;
    id: number;
    name: string;
    imagePath: string;
    grade: string;
    htmlContent: string;
    linkName: string;
    completed: boolean;
    questions: IQuestion[]
}