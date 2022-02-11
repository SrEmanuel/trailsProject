import { Link } from "react-router-dom";
import { FiLogIn } from "react-icons/fi";

import "./styles.scss";
import { ReactComponent as Logo } from "../../assets/images/Logo.svg";

export function NavBar() {
  return (
    <nav className="navbar">
      <Logo />
      <div>
        <Link to="#">Trilhas</Link>
        <Link to="#">Sobre</Link>
        <Link to="#">Cadastre-se</Link>
        <button>
          <FiLogIn color="var(--white)" size={18} />
          Entrar
        </button>
      </div>
    </nav>
  );
}
