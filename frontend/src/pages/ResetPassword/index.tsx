import { WaveContainer } from "../../components/WaveContainer";
import logo from "../../assets/images/Logo.svg";
import { useNavigate } from "react-router-dom";
import { ForestContainer } from "../../components/ForestContainer/indext";

export function ResetPassword() {
  const navigate = useNavigate();

  return (
    <WaveContainer>
      <ForestContainer>
        <main className="password-container">
          <img onClick={() => navigate("/")} src={logo} alt="Projeto trilhas" />
          <h1>Insira a sua nova senha</h1>
          <span>
            Pense em uma senha com mais de 8 dígitos na qual você consiga se
            lembrar
          </span>

          <form>
            <input type="password" name="password" placeholder="Nova senha" />
            <input type="password" name="confirmPassword" placeholder="Confirmar senha" />
            <button>Enviar</button>
          </form>
        </main>
      </ForestContainer>
    </WaveContainer>
  );
}
