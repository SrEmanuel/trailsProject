import React from "react";
import { AuthContextProvider } from "./contexts/AuthContext";
import { Routes } from "./routes";

function App() {
  return (
    <AuthContextProvider>
      <Routes />
    </AuthContextProvider>
  );
}

export default App;
