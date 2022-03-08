import { useNavigate } from "react-router-dom";
import { Formik } from "formik";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

import { WaveContainer } from "../../components/WaveContainer";
import logo from "../../assets/images/Logo.svg";
import { RegisterSchema } from "../../schemas/register.schema";
import api from "../../services/api";

import "./styles.scss";

interface IRegisterSchema {
  name: string;
  email: string;
  password: string;
  confirmPassword: string;
}

export function Register() {
  const navigate = useNavigate();

  async function handleSubmitUserData(data: IRegisterSchema) {
    try {
      await api.post("/users", data);
    } catch (error: any) {
      toast.error(error.response.data.message || error.response.data[0].message);
    }
  }

  return (
    <WaveContainer>
      <ToastContainer />
      <img
        onClick={() => navigate("/")}
        className="left-side-floating-logo"
        src={logo}
        alt="Projeto Trilhas"
      />
      <main className="register-container">
        <Formik
          initialValues={{
            name: "",
            email: "",
            password: "",
            confirmPassword: "",
          }}
          validationSchema={RegisterSchema}
          onSubmit={(values) => handleSubmitUserData(values)}
        >
          {({ handleChange, handleSubmit, values, errors, touched }) => (
            <form>
              <h1>Cadastro de usuário</h1>
              <h2>Dados</h2>
              <div className="input-container">
                <div className="input-group">
                  <label htmlFor="name">Nome:</label>
                  <input onChange={handleChange} type="text" name="name" />
                  {errors.name && touched.name && (
                    <span className="error">{errors.name}</span>
                  )}
                </div>
                <div className="input-group">
                  <label htmlFor="email">Email:</label>
                  <input onChange={handleChange} type="email" name="email" />
                  {errors.email && touched.email && (
                    <span className="error">{errors.email}</span>
                  )}
                </div>
                <div className="input-group">
                  <label htmlFor="password">Senha:</label>
                  <input
                    onChange={handleChange}
                    type="password"
                    name="password"
                  />
                  {errors.password && touched.password && (
                    <span className="error">{errors.password}</span>
                  )}
                </div>
                <div className="input-group">
                  <label htmlFor="confirmPassword">Confirmar senha:</label>
                  <input
                    onChange={handleChange}
                    type="password"
                    name="confirmPassword"
                  />
                  {errors.confirmPassword && touched.confirmPassword && (
                    <span className="error">{errors.confirmPassword}</span>
                  )}
                </div>
              </div>
              <div className="buttons-container">
                <button>Cancelar</button>
                <button type="button" onClick={() => handleSubmit()}>
                  Enviar
                </button>
              </div>
            </form>
          )}
        </Formik>
      </main>
    </WaveContainer>
  );
}
