import { useState } from "react";
import { FiMenu } from "react-icons/fi";
import { Link } from "react-router-dom";
import "./styles.scss";

export function SideNav() {
  const [isVisible, setIsVisible] = useState(false);

  function toggleSidebar() {
    setIsVisible(!isVisible);
  }

  return (
    <>
      <FiMenu
        onClick={toggleSidebar}
        className="hamburguer"
        color="var(--green)"
        size={24}
      />

      <div className={`menu ${isVisible ? "active" : ""}`}>
        <Link to="#">Trilhas</Link>
        <Link to="#">Sobre</Link>
        <Link to="#">Cadastre-se</Link>
      </div>
    </>
  );
}
