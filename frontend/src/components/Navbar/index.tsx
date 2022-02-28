import { Link, useNavigate } from "react-router-dom";
import { FiLogIn } from "react-icons/fi";

import  Logo  from "../../assets/images/Logo.svg";
import { SideMenu } from "../SideMenu";
import "./styles.scss";

export function NavBar() {

  const navigate = useNavigate();

  return (
    <nav className="navbar">
      <img src={Logo} onClick={ ()=> navigate('/') } alt="Projeto Trilhas" />
      <div>
        <Link to="/cursos">Trilhas</Link>
        <Link to="/sobre">Sobre</Link>
        <Link to="/cadastro">Cadastre-se</Link>
        <Link className="login-btn" to ="/login">
          <FiLogIn color="var(--white)" size={18} />
          Entrar
        </Link>
        <SideMenu />
      </div>
    </nav>
  );
}
