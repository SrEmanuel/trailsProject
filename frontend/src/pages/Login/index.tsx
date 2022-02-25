import { WaveContainer } from "../../components/WaveContainer";
import Logo from "../../assets/images/Logo.svg";
import { FiLogIn } from 'react-icons/fi';

import "./styles.scss";
import { Link, useNavigate } from "react-router-dom";

export function Login() {
  const navigate = useNavigate()

  return (
    <WaveContainer>
      <main className="login-content">
      <img onClick={()=> navigate('/')} src={Logo} alt="Projeto Trilhas" />
      <h1>Faça seu login para continuar</h1>
      <form className="login-form">
        <input type="email" name="email" placeholder="Email" />
        <input type="password" name="password" placeholder="Senha" />

        <Link to="#" className="forgot-password">
          Esqueceu sua senha? Clique aqui!
        </Link>
        <button>
            <FiLogIn color="var(--white)" size={24} />
            Entrar
        </button>
      </form>
      </main>
    </WaveContainer>
  );
}
