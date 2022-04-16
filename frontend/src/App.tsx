import React from "react";
import { ToastContainer } from "react-toastify";
import { AuthContextProvider } from "./contexts/AuthContext";
import { Routes } from "./routes";

function App() {
  return (
    <AuthContextProvider>
      <ToastContainer />
      <Routes />
    </AuthContextProvider>
  );
}

export default App;
