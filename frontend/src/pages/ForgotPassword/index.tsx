import { WaveContainer } from "../../components/WaveContainer";
import logo from "../../assets/images/Logo.svg";
import "./styles.scss";
import { useNavigate } from "react-router-dom";
import { ForestContainer } from "../../components/ForestContainer/indext";
import { Formik } from "formik";
import * as Yup from "yup";
import api from "../../services/api";
import { toast } from "react-toastify";

export function ForgotPassword() {
  const navigate = useNavigate();

  const EmailSchema = Yup.string()
    .email("O e-mail precisa ser válido")
    .required("O e-mail é um campo necessário");

  async function handleSubmitEmail(value: { email: string }) {
    try {
      const response = await api.post("/auth/forgot", value);
      toast.success(response.data.message);
    } catch (error: any) {
      toast.error(error.message);
    }
  }

  return (
    <WaveContainer>
      <ForestContainer>
        <main className="password-container">
          <img onClick={() => navigate("/")} src={logo} alt="Projeto trilhas" />
          <h1>Se perdeu? Recupere sua senha</h1>
          <span>
            Informe um e-mail para enviarmos as instruções de recuperação
          </span>

          <Formik
            initialValues={{ email: "" }}
            validationSchema={Yup.object().shape({
              email: EmailSchema,
            })}
            onSubmit={(values) => handleSubmitEmail(values)}
          >
            {({ handleChange, handleSubmit, touched, errors }) => (
              <form>
                <input
                  onChange={handleChange}
                  type="email"
                  name="email"
                  placeholder="E-mail"
                />
                {errors.email && touched.email && (
                  <span className=" text error"> {errors.email} </span>
                )}
                <button type="button" onClick={() => handleSubmit()}>
                  Enviar
                </button>
              </form>
            )}
          </Formik>
        </main>
      </ForestContainer>
    </WaveContainer>
  );
}
