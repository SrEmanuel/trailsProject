import { Link, useLocation, useNavigate } from "react-router-dom";
import { FiLogIn, FiLogOut } from "react-icons/fi";

import Logo from "../../assets/images/Logo.svg";
import { SideMenu } from "../SideMenu";
import "./styles.scss";
import { useAuth } from "../../hooks/useAuth";
import { useEffect, useState } from "react";
import { Profile } from "../Profile";

export function NavBar() {
  const { handleClearUserDataFromStorage, getUser } = useAuth();
  const [hasLoggedUser, setHasLoggedUser] = useState<boolean>(false);
  const [showUserProfile, setShowUserProfile] = useState<boolean>(false);
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const user = getUser();
    user ? setHasLoggedUser(true) : setHasLoggedUser(false);
  }, [location, getUser]);

  return (
    <nav className="navbar">
      <img src={Logo} onClick={() => navigate("/")} alt="Projeto Trilhas" />
      <div>
        <Link to="/cursos">Trilhas</Link>
        <Link to="/sobre">Sobre</Link>
        {!hasLoggedUser ? (
          <>
            <Link to="/cadastro">Cadastre-se</Link>
            <Link className="login-btn" to="/login">
              <FiLogIn color="var(--white)" size={18} />
              Entrar
            </Link>
          </>
        ) : (
         <>
         <span className="nav-item" onClick={ () => setShowUserProfile(true)} > Meu perfil </span>
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
        <SideMenu hasLoggedUser={hasLoggedUser} />
        <Profile isVisible={showUserProfile} setIsVisible={setShowUserProfile} />
      </div>
    </nav>
  );
}
