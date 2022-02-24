import { WaveContainer } from "../../components/WaveContainer";
import logo from "../../assets/images/Logo.svg";
import "./styles.scss";

export function Register() {
  return (
    <WaveContainer>
      <img className="left-side-floating-logo" src={logo} alt="Projeto Trilhas" />
      <main className="register-container">
        <form>
        <h1>Cadastro de professor</h1>
        <h2>Dados</h2>
          <div className="input-container">
            <div className="input-group">
              <label htmlFor="name">Nome:</label>
              <input type="text" name="name" />
            </div>
            <div className="input-group">
              <label htmlFor="email">Email:</label>
              <input type="email" name="email" />
            </div>
            <div className="input-group">
              <label htmlFor="password">Senha:</label>
              <input type="password" name="password" />
            </div>
            <div className="input-group">
              <label htmlFor="confirmPassword">Confirmar senha:</label>
              <input type="password" name="confirmPassword" />
            </div>
          </div>
          <div className="buttons-container">
            <button>Cancelar</button>
            <button>Enviar</button>
          </div>
        </form>
      </main>
    </WaveContainer>
  );
}
