export interface IUser {
  name: string;
  email: string;
  id: string;
  enabled: boolean;
  authorities: string[];
  token: string;
}