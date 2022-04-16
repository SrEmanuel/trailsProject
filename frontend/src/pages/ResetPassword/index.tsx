import { WaveContainer } from "../../components/WaveContainer";
import logo from "../../assets/images/Logo.svg";
import { useLocation, useNavigate } from "react-router-dom";
import { ForestContainer } from "../../components/ForestContainer/indext";
import { Formik } from "formik";
import { ResetPasswordSchema } from "../../schemas/resetPasswordSchema";
import { toast } from "react-toastify";
import api from "../../services/api";

export function ResetPassword() {
  const navigate = useNavigate();
  const location = useLocation();

  async function handleSubmitNewPassword(values: {
    password: string;
    confirmPassword: string;
  }) {
    const token = location.search;
    try {
      const response = await api.post(`/auth/change-password${token}`, values);
      toast.success(response.data.message);
      setTimeout(()=> navigate('/login'), 2000)
    } catch (error: any) {
      toast.error(error.data.message);
    }
  }

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

          <Formik
            initialValues={{ password: "", confirmPassword: "" }}
            validationSchema={ResetPasswordSchema}
            onSubmit={(values) => handleSubmitNewPassword(values)}
          >
            {({ handleChange, handleSubmit, errors, touched }) => (
              <form>
                <input
                  onChange={handleChange}
                  type="password"
                  name="password"
                  placeholder="Nova senha"
                />

                {errors.password && touched.password && (
                  <span className="error text">{errors.password}</span>
                )}

                <input
                  onChange={handleChange}
                  type="password"
                  name="confirmPassword"
                  placeholder="Confirmar senha"
                />

                {errors.confirmPassword && touched.confirmPassword && (
                  <span className="error text">{errors.confirmPassword}</span>
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
