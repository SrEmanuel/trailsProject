import { Formik } from "formik";
import { FiX } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import * as Yup from "yup";

import { ModalContainer } from "../../../../components/ModalContainer";
import { Overlay } from "../../../../components/Overlay";
import { useAuth } from "../../../../hooks/useAuth";
import { ITopic } from "../../../../interfaces/topic";
import { ITrails } from "../../../../interfaces/Trail";
import api from "../../../../services/api";
import { handleNotifyError } from "../../../../utils/handleNotifyError";

import "./styles.scss";

interface Props {
  isVisible: boolean;
  currentCourse: ITrails;
  setIsVisible: (arg: boolean) => void;
  setTopics: React.Dispatch<React.SetStateAction<ITopic[] | undefined>>;
}

export function AddNewSection({ isVisible, currentCourse , setIsVisible, setTopics }: Props) {
  const { handleClearUserDataFromStorage } = useAuth();
  const navigate = useNavigate();

  async function handleSumitValuesAndClose(name: string){
      const topic = {
        name: name,
        position: 100,
        courseId: currentCourse?.id,
      };
      try {
        await api.post("/topics", topic);
        const response = await api.get(
          `/courses/${currentCourse?.linkName}/topics`
        );
        setTopics(response.data.content);
      } catch (error) {
        handleNotifyError(error, navigate, handleClearUserDataFromStorage);
      }
    setIsVisible(false);
  }

  return (
    <Overlay hidden={!isVisible}>
      <ModalContainer>
        <FiX size={24} color="var(--red)" onClick={() => setIsVisible(false)} />
        <Formik
          initialValues={{ name: "" }}
          validationSchema={Yup.object().shape({
            name: Yup.string().required("O nome é obrigatório"),
          })}
          onSubmit={(values) =>  handleSumitValuesAndClose(values.name) }
        >
          {({ handleChange, handleSubmit, errors, touched }) => (
            <>
              <h2>Deseja adicionar uma nova sessão?</h2>
              <input
                name="name"
                onChange={handleChange}
                placeholder="Nome da sessão"
              />
              { errors.name && touched.name && <span className="error" >{errors.name}</span> }
              <div className="btn-container">
                <button type="button" onClick={() => setIsVisible(false)}>
                  Cancelar
                </button>
                <button type="submit" onClick={handleSubmit as any}>
                  Criar
                </button>
              </div>
            </>
          )}
        </Formik>
      </ModalContainer>
    </Overlay>
  );
}
