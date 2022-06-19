export interface IUser {
  name: string;
  email: string;
  id: string;
  enabled: boolean;
  roles: string[];
  token: string;
  password: string;
}