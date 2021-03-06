import { useState } from "react";
import { FiMenu, FiBookOpen, FiInfo, FiLogIn, FiLogOut, FiUser, FiActivity } from "react-icons/fi";
import { Link } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";
import "./styles.scss";

interface Props {
  hasLoggedUser: boolean;
  setShowUserProfile: (arg: boolean) => void;
}

export function SideMenu({ hasLoggedUser, setShowUserProfile }: Props) {
  const [isVisible, setIsVisible] = useState(false);
  const { handleClearUserDataFromStorage } = useAuth();

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
        <Link to="/sobre">
          <FiInfo color="var(--green)" size={24} />
          <span>Sobre</span>
        </Link>
        {!hasLoggedUser ? (
          <>
            <Link to="/cadastro">
              <FiLogIn color="var(--green)" size={24} />
              <span>Cadastre-se</span>
            </Link>
            <Link className="login-btn" to="/login">
              <FiLogIn color="var(--white)" size={18} />
              Entrar
            </Link>
          </>
        ) : (
          <>
            <span className="nav-item" onClick={() => { setShowUserProfile(true); setIsVisible(false) }}>
              <FiUser color="var(--green)" size={24}  />
              Meu perfil
            </span>
            <Link to="/meu-desempenho">
              <FiActivity color="var(--green)" size={24} />
              Meu desempenho
              </Link>
            <Link
              onClick={() => handleClearUserDataFromStorage()}
              className="logout-btn"
              to="/"
            >
              <FiLogOut color="var(--white)" size={18} />
              Sair
            </Link>
          </>
        )}
      </aside>
    </>
  );
}
