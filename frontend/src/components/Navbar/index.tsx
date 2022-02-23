import { Link, useNavigate } from "react-router-dom";
import { FiLogIn } from "react-icons/fi";

import { ReactComponent as Logo } from "../../assets/images/Logo.svg";
import { SideMenu } from "../SideMenu";
import "./styles.scss";

export function NavBar() {

  const navigate = useNavigate();

  return (
    <nav className="navbar">
      <Logo onClick={ ()=> navigate('/') } />
      <div>
        <Link to="/cursos">Trilhas</Link>
        <Link to="#">Sobre</Link>
        <Link to="#">Cadastre-se</Link>
        <Link className="login-btn" to ="/login">
          <FiLogIn color="var(--white)" size={18} />
          Entrar
        </Link>
        <SideMenu />
      </div>
    </nav>
  );
}
