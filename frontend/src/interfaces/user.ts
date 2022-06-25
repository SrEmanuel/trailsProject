export interface IUser {
  name: string;
  email: string;
  username: string;
  id: string;
  enabled: boolean;
  roles: string[];
  token: string;
  password: string;
  imagePath: string;
}