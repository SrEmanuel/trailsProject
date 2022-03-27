import { useRef, useState } from "react";
import { FiArrowLeft } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { CKEditor } from "ckeditor4-react";
import { Dropzone } from "../../../components/Dropzone";
import { WaveContainer } from "../../../components/WaveContainer";
import "../styles.scss";
import "./styles.scss";
import { Formik } from "formik";
import { NewContentSchema } from "../../../schemas/newcontent.schema";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import api from "../../../services/api";

interface PostData {
  title: string;
  grade: string;
  image?: File | string;
  htmlContent?: string;
}

export function CreateContent() {
  const [step, setStep] = useState(1);
  const navigate = useNavigate();
  const formikRef = useRef() as any;

  async function handleSubmit(values: PostData) {
    console.log(values);
    if (step === 1) {
      setStep(2);
    } else {
      try {
        const file = values.image as File;
        const formatedFileName =
          file.name.toLowerCase().replaceAll(" ", "-") + "." + file.type;
        delete values.image;

        const data = new FormData();
        data.append("subject", JSON.stringify(values));
        data.append("image", {
          name: formatedFileName,
          type: file.type,
          uri: URL.createObjectURL(file),
        } as any);

        await api.post("/subjects", data);
      } catch (error: any) {
        console.log(error.data);
      }
    }
  }

  function inputHandler(event: any, editor: any) {
    formikRef?.current?.setFieldValue("htmlContent", event.editor.getData());
  }

  function imgHandler(event: any) {
    formikRef.current.setFieldValue("image", event.target.files[0]);
  }

  return (
    <WaveContainer>
      <span className="goBack" onClick={() => navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <main className="create-container">
        <h1>Criar nova postagem</h1>
        <Formik
          innerRef={formikRef}
          initialValues={{ grade: "", title: "", image: "", htmlContent: "" }}
          validationSchema={NewContentSchema}
          onSubmit={(values) => handleSubmit(values)}
        >
          {({ handleSubmit, handleChange, errors, touched }) => (
            <form>
              {step === 1 ? (
                <>
                  <Dropzone onChange={imgHandler} />
                  {errors.image && touched.image && (
                    <span className="error text">{errors.image}</span>
                  )}
                  <input
                    onChange={handleChange}
                    type="text"
                    name="title"
                    placeholder="Título"
                  />
                  {errors.title && touched.title && (
                    <span className="error text">{errors.title}</span>
                  )}

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
                  {errors.grade && touched.grade && (
                    <span className="error text">{errors.grade}</span>
                  )}
                </>
              ) : (
                <CKEditor
                  editor={ClassicEditor}
                  style={{ width: "90%" }}
                  initData={
                    <p>
                      Este é o seu editor. Use-o para criar seus textos e
                      postagens!
                    </p>
                  }
                  onChange={inputHandler as any}
                />
              )}
              <div className="buttons-container">
                <button
                  type="button"
                  onClick={() => (step === 1 ? navigate(-1) : setStep(1))}
                >
                  {step === 1 ? "Cancelar" : "Voltar"}
                </button>
                <button type="button" onClick={() => handleSubmit()}>
                  {step === 1 ? "Avançar" : "Terminar"}
                </button>
              </div>
            </form>
          )}
        </Formik>
      </main>
    </WaveContainer>
  );
}
