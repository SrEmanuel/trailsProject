import { createContext, ReactNode, useState } from "react";

interface IUser {
  name: string;
  email: string;
  id: string;
  status: boolean;
  profiles: string[];
  token: string;
}

interface IAuthContextProps {
  getUser: () => Promise<IUser | undefined>;
  handleSavaUserDataToStorage: (user: IUser) => Promise<void>;
  handleLoadUserDataFromStorage: () => Promise<void>;
  handleClearUserDataFromStorage: () => Promise<void>;
}

interface IAuthContextProviderProps {
  children: ReactNode;
}

const AuthContext = createContext({} as IAuthContextProps);

export function AuthContextProvider(props: IAuthContextProviderProps) {
  const [user, setUser] = useState<IUser>();

  async function getUser(): Promise<IUser | undefined> {
    return user;
  }

  async function handleSavaUserDataToStorage(user: IUser): Promise<void> {
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
  }

  return (
    <AuthContext.Provider
      value={{
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
