import { useEffect, useRef } from "react";
import { Formik } from "formik";
import { FiX } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import * as Yup from "yup";

import { ModalContainer } from "../ModalContainer";
import { Overlay } from "../Overlay";
import { useAuth } from "../../hooks/useAuth";
import { ITopic } from "../../interfaces/topic";
import { ITrails } from "../../interfaces/Trail";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";

import "./styles.scss";

interface Props {
  isVisible: boolean;
  currentCourse?: ITrails;
  currentCourseLinkName?: string;
  mode: "create" | "update";
  topic?: ITopic;
  setIsVisible: (arg: boolean) => void;
  setTopics: React.Dispatch<React.SetStateAction<ITopic[] | undefined>>;
}

export function AddOrUpdateSection({
  isVisible,
  currentCourse,
  currentCourseLinkName,
  mode,
  topic,
  setIsVisible,
  setTopics,
}: Props) {
  const { handleClearUserDataFromStorage } = useAuth();
  const navigate = useNavigate();
  const formikRef = useRef() as any;

  async function handleSumitValuesAndClose(name: string) {
    try {
      if (mode === "create") {
        const data = {
          name: name,
          position: 100,
          courseId: currentCourse?.id,
        };
        await api.post("/topics", data);
        const response = await api.get(
          `/courses/${currentCourse?.linkName}/topics`
        );
        setTopics(response.data.content);
      } else {
        const data = { name, position: 100 }
        await api.put(`/topics/${topic?.linkName}`, data);
        const response = await api.get(
          `/courses/${currentCourseLinkName}/topics`
        );
        setTopics(response.data.content);
      }
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
    setIsVisible(false);
  }

  useEffect(() => {
    formikRef.current.setFieldValue("name", topic?.name);
  }, [topic]);

  return (
    <Overlay hidden={!isVisible}>
      <ModalContainer>
        <FiX size={24} color="var(--red)" onClick={() => setIsVisible(false)} />
        <Formik
          innerRef={formikRef}
          initialValues={{ name: "" }}
          validationSchema={Yup.object().shape({
            name: Yup.string().required("O nome é obrigatório"),
          })}
          onSubmit={(values) => handleSumitValuesAndClose(values.name)}
        >
          {({ handleChange, handleSubmit, errors, touched, values }) => (
            <>
              <h2>
                {mode === "create"
                  ? "Deseja adicionar uma nova sessão?"
                  : "Atualizar sessão"}
              </h2>
              <input
                name="name"
                defaultValue={values.name}
                onChange={handleChange}
                placeholder="Nome da sessão"
              />
              {errors.name && touched.name && (
                <span className="error">{errors.name}</span>
              )}
              <div className="btn-container">
                <button type="button" onClick={() => setIsVisible(false)}>
                  Cancelar
                </button>
                <button type="submit" onClick={handleSubmit as any}>
                  {mode === "create" ? "Criar" : "Atualizar"}
                </button>
              </div>
            </>
          )}
        </Formik>
      </ModalContainer>
    </Overlay>
  );
}
