import { WaveContainer } from "../../components/WaveContainer";
import Logo from "../../assets/images/Logo.svg";
import { FiLogIn } from "react-icons/fi";

import "./styles.scss";
import { Link, useNavigate } from "react-router-dom";
import api from "../../services/api";
import { toast, ToastContainer } from "react-toastify";
import { Formik } from "formik";
import { LoginSchema } from "../../schemas/login.schema";
import { useAuth } from "../../hooks/useAuth";

interface ICredentials {
  email: string;
  password: string;
}

export function Login() {
  const navigate = useNavigate();
  const { handleSavaUserDataToStorage } = useAuth();

  async function handleLogin(credentials: ICredentials) {
    try {
      const response =  await api.post("/login", credentials);
      await handleSavaUserDataToStorage(response.data);
      toast.success("Login realizado com sucesso!");
      setTimeout(() => navigate('/cursos') , 2000)
    } catch (error: any) {
      toast.error(
        error.response.data.message || error.response.data[0].message
      );
    }
  }

  return (
    <WaveContainer>
      <ToastContainer />
      <main className="login-content">
        <img onClick={() => navigate("/")} src={Logo} alt="Projeto Trilhas" />
        <h1>Faça seu login para continuar</h1>
        <Formik
          initialValues={{ email: "", password: "" }}
          validationSchema={LoginSchema}
          onSubmit={(values) => handleLogin(values)}
        >
          {({ handleSubmit, handleChange, errors, touched }) => (
            <form className="login-form">
              <input
                onChange={handleChange}
                type="email"
                name="email"
                placeholder="Email"
              />
              {errors.email && touched.email && (
                <span className="error">{errors.email}</span>
              )}
              <input
                onChange={handleChange}
                type="password"
                name="password"
                placeholder="Senha"
              />
              {errors.password && touched.password && (
                <span className="error">{errors.password}</span>
              )}

              <Link to="/esqueci-minha-senha" className="forgot-password">
                Esqueceu sua senha? Clique aqui!
              </Link>
              <button type="button" onClick={() => handleSubmit()}>
                <FiLogIn color="var(--white)" size={24} />
                Entrar
              </button>
            </form>
          )}
        </Formik>
      </main>
    </WaveContainer>
  );
}
