import { WaveContainer } from "../../components/WaveContainer";
import logo from "../../assets/images/Logo.svg";
import "./styles.scss";
import { useNavigate } from "react-router-dom";
import { ForestContainer } from "../../components/ForestContainer/indext";

export function ForgotPassword() {
  const navigate = useNavigate();

  return (
    <WaveContainer>
      <ForestContainer>
        <main className="password-container">
          <img onClick={() => navigate("/")} src={logo} alt="Projeto trilhas" />
          <h1>Se perdeu? Recupere sua senha</h1>
          <span>
            Informe um e-mail para enviarmos as instruções de recuperação
          </span>

          <form>
            <input type="email" name="email" placeholder="E-mail" />
            <button>Enviar</button>
          </form>
        </main>
      </ForestContainer>
    </WaveContainer>
  );
}
