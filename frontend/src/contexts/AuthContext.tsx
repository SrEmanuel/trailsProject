import { createContext, ReactNode, useEffect, useState } from "react";
import { IUser } from "../interfaces/user";

interface IAuthContextProps {
  user: IUser | undefined;
  roles: string[];
  getUser: () => IUser | undefined;
  saveRoles: (user: IUser) => Promise<void>;
  handleSavaUserDataToStorage: (user: IUser) => Promise<void>;
  handleLoadUserDataFromStorage: () => Promise<void>;
  handleClearUserDataFromStorage: () => Promise<void>;
}

interface IAuthContextProviderProps {
  children: ReactNode;
}

export const AuthContext = createContext({} as IAuthContextProps);

export function AuthContextProvider(props: IAuthContextProviderProps) {
  const [user, setUser] = useState<IUser>();
  const [roles, setRoles] = useState<string[]>([]);

  useEffect(() => {
    handleLoadUserDataFromStorage();
  }, []);

  function getUser(): IUser | undefined {
    return user;
  }

  async function saveRoles(user: IUser): Promise<void> {
    setRoles(user.authorities.map((role) => role.authority));
  }

  async function handleSavaUserDataToStorage(user: IUser): Promise<void> {
    localStorage.setItem("user", JSON.stringify(user));
    setUser(user);
  }

  async function handleLoadUserDataFromStorage(): Promise<void> {
    const userData = localStorage.getItem("user");
    if (userData) {
      setUser(JSON.parse(userData));
      await saveRoles(JSON.parse(userData));
    }
  }

  async function handleClearUserDataFromStorage(): Promise<void> {
    localStorage.removeItem("user");
    setUser(undefined);
  }

  return (
    <AuthContext.Provider
      value={{
        user,
        roles,
        getUser,
        saveRoles,
        handleSavaUserDataToStorage,
        handleLoadUserDataFromStorage,
        handleClearUserDataFromStorage,
      }}
    >
      {props.children}
    </AuthContext.Provider>
  );
}
