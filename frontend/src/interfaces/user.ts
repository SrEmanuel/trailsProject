export interface IUser {
  name: string;
  email: string;
  id: string;
  enabled: boolean;
  authorities: { authority: string }[];
  token: string;
}