import { useState } from "react";
import { FiMenu, FiBookOpen, FiInfo, FiLogIn } from "react-icons/fi";
import { Link } from "react-router-dom";
import "./styles.scss";

export function SideMenu() {
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

      <aside className={`menu ${isVisible ? "active" : ""}`}>
        <Link to="/cursos">
          <FiBookOpen color="var(--green)" size={24} />
          <span>Trilhas</span>
        </Link>
        <Link to="#">
          <FiInfo color="var(--green)" size={24} />
          <span>Sobre</span>
        </Link>
        <Link to="#">
          <FiLogIn color="var(--green)" size={24} />
          <span>Cadastre-se</span>
        </Link>
        <Link className="login-btn" to="/login" >
          <FiLogIn color="var(--white)" size={18} />
          Entrar
        </Link>
      </aside>
    </>
  );
}
