import { Formik } from "formik";
import { FiX } from "react-icons/fi";
import * as Yup from "yup";

import { ModalContainer } from "../../../../components/ModalContainer";
import { Overlay } from "../../../../components/Overlay";

import "./styles.scss";

interface Props {
  isVisible: boolean;
  setIsVisible: (arg: boolean) => void;
  setSection: React.Dispatch<React.SetStateAction<string | undefined>>;
}

export function AddNewSection({ isVisible, setIsVisible, setSection }: Props) {

  function handleSumitValuesAndClose(name: string){
    setSection(name);
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
