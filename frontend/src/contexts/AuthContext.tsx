import { createContext, ReactNode, useEffect, useState } from "react";
import { IUser } from "../interfaces/user";

interface IAuthContextProps {
  user: IUser | undefined;
  getUser: () => IUser | undefined;
  handleSavaUserDataToStorage: (user: IUser, token: string) => Promise<void>;
  handleLoadUserDataFromStorage: () => Promise<void>;
  handleClearUserDataFromStorage: () => Promise<void>;
}

interface IAuthContextProviderProps {
  children: ReactNode;
}

export const AuthContext = createContext({} as IAuthContextProps);

export function AuthContextProvider(props: IAuthContextProviderProps) {
  const [user, setUser] = useState<IUser>();

  useEffect(() => {
    async function execute() {
      await handleLoadUserDataFromStorage();
    }

    execute();
  }, []);

  function getUser(): IUser | undefined {
    return user;
  }

  async function handleSavaUserDataToStorage(
    user: IUser,
    token: string
  ): Promise<void> {
    user.token = token;
    localStorage.setItem("user", JSON.stringify(user));
    setUser(user);
  }

  async function handleLoadUserDataFromStorage(): Promise<void> {
    const userData = localStorage.getItem("user");
    if (userData) {
      setUser(JSON.parse(userData));
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
        getUser,
        handleSavaUserDataToStorage,
        handleLoadUserDataFromStorage,
        handleClearUserDataFromStorage,
      }}
    >
      {props.children}
    </AuthContext.Provider>
  );
}
