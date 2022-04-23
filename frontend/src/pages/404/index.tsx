import { NavBar } from "../../components/Navbar";
import Trails from "../../assets/images/trails.svg";
import { ReactComponent as NotFound} from '../../assets/images/404.svg';
import "./styles.scss";
import { Link } from "react-router-dom";

export function PageNotFound() {
  return (
    <div className="container">
      <NavBar />
      <div className="page-not-found-content">
        <img src={Trails} alt="imagem de homem perdido em trilha" />
        <main className="message">
          <NotFound />
          <h1>Ops! Saiu da trilha?</h1>
          <span className="text">
            Não se preocupe em ficar perdido, nós te colocamos de volta no
            caminho!
          </span>
          <Link className="link-btn" to="/cursos" >Voltar as trilhas</Link>
        </main>
      </div>
    </div>
  );
}
