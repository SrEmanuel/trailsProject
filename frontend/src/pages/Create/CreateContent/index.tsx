import { useState } from "react";
import { FiArrowLeft } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { CKEditor } from "ckeditor4-react";
import { Dropzone } from "../../../components/Dropzone";
import { WaveContainer } from "../../../components/WaveContainer";
import "../styles.scss";
import "./styles.scss";
import { Formik } from "formik";
import { NewContentSchema } from "../../../schemas/newcontent.schema";

export function CreateContent() {
  const [step, setStep] = useState(1);
  const [stepOneData, setStepOneData] = useState();
  const navigate = useNavigate();

  function handleSubmitStepOne(values: any) {
    setStepOneData(values);
    setStep(2);
  }

  return (
    <WaveContainer>
      <span className="goBack" onClick={() => navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <main className="create-container">
        <h1>Criar nova postagem</h1>
        {step === 1 ? (
          <Formik
            initialValues={{ grade: "", title: "" }}
            validationSchema={NewContentSchema}
            onSubmit={(values) => handleSubmitStepOne(values)}
          >
            {({ handleSubmit, handleChange, errors, touched }) => (
              <form>
                <Dropzone />
                <input
                  onChange={handleChange}
                  type="text"
                  name="title"
                  placeholder="Título"
                />
                {errors.title && touched.title && <span className="error text">{errors.title}</span>}

                <select
                  onChange={handleChange}
                  name="grade"
                  placeholder="Selecione uma série"
                >
                  <option selected disabled hidden value="0">
                    Selecione uma opção
                  </option>
                  <option value="1">1º ano</option>
                  <option value="2">2º ano</option>
                  <option value="3">3º ano</option>
                </select>
                {errors.grade && touched.grade && <span className="error text">{errors.grade}</span>}
                <div className="buttons-container">
                  <button>Cancelar</button>
                  <button type="button" onClick={() => handleSubmit()}>
                    Avançar
                  </button>
                </div>
              </form>
            )}
          </Formik>
        ) : (
          <>
            <CKEditor
              style={{ width: "90%" }}
              initData={
                <p>This is an example CKEditor 4 WYSIWYG editor instance.</p>
              }
            />
            <div className="buttons-container">
              <button>Voltar</button>
              <button onClick={() => setStep(2)}>Concluir</button>
            </div>
          </>
        )}
      </main>
    </WaveContainer>
  );
}
