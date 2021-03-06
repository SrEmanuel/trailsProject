import { FiLogIn } from "react-icons/fi";
import { NavBar } from "../../components/Navbar";
import Trails from "../../assets/images/trails.svg";

import "./styles.scss";
import { Link } from "react-router-dom";
import { useAuth } from "../../hooks/useAuth";

export function Home() {
  const { user } = useAuth();

  return (
    <div className="container">
      <NavBar />
      <div className="home-content">
        <section>
          <h1>Não sabe qual caminho de estudos trilhar?</h1>
          <span className="text">Projeto trilhas vai te ajudar!</span>
          <div className="button-container">
            <div>
              <FiLogIn color="var(--white)" size={24} />
            </div>
            <Link to={ user? "/cursos" : "/cadastro"} >{ user? "Acessar trilhas" : "Cadastre-se"}</Link>
          </div>
        </section>
        <img src={Trails} alt="imagem de homem perdido em trilha" />
      </div>
    </div>
  );
}
