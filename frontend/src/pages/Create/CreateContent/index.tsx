import {
  useCallback,
  useEffect,
  useRef,
  useState,
} from "react";
import { FiArrowLeft } from "react-icons/fi";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { CKEditor } from "ckeditor4-react";
import { Dropzone } from "../../../components/Dropzone";
import { WaveContainer } from "../../../components/WaveContainer";
import "../styles.scss";
import "./styles.scss";
import { Formik, FormikProps } from "formik";
import { NewContentSchema } from "../../../schemas/newcontent.schema";
import ClassicEditor from "@ckeditor/ckeditor5-build-classic";
import api from "../../../services/api";
import { toast } from "react-toastify";
import { useAuth } from "../../../hooks/useAuth";
import { handleNotifyError } from "../../../utils/handleNotifyError";

interface PostData {
  name: string;
  grade: string;
  image?: File | string;
  htmlContent?: string;
}

export function CreateContent() {
  const [step, setStep] = useState(1);
  const [initialValues, setInitialValues] = useState<PostData>({
    grade: "",
    name: "",
    image: "",
    htmlContent: "",
  });
  const navigate = useNavigate();
  const { handleClearUserDataFromStorage } = useAuth();
  const formikRef = useRef<FormikProps<PostData>>(null);
  const params = useParams();
  const location = useLocation();

  const loadCurrentSubject = useCallback(async () => {
    try {
      const response = await api.get(`/subjects/${params.contentname}`);
      return response.data;
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }, [handleClearUserDataFromStorage, navigate, params.contentname]);

  async function handleSubmit(values: PostData) {
    if (step === 1) {
      setStep(2);
    } else {
      try {
        const image = values.image as File;
        delete values.image;
        const subject = { ...values, topicId: params.topicId, position: 2 };
        /*const formatedFileName =
          file.name.toLowerCase().replaceAll(" ", "-") + "." + file.type;*/

        const data = new FormData();
        data.append("image", image);

        const response = await api.post("/subjects", subject);
        const subjectLinkName = response.data.linkName;
        await api.post(`/subjects/${subjectLinkName}/add-image`, data);
        toast.success("Conteúdo criado com sucesso!");
        navigate(-1);
      } catch (error: any) {
        handleNotifyError(error, navigate, handleClearUserDataFromStorage);
      }
    }
  }

  function inputHandler(event: any) {
    formikRef?.current?.setFieldValue("htmlContent", event.editor.getData());
  }

  function imgHandler(event: any) {
    formikRef?.current?.setFieldValue("image", event.target.files[0]);
  }

  useEffect(() => {
    if (location.pathname.includes("atualizar")) {
      loadCurrentSubject().then((subject) => {
        formikRef.current?.setFieldValue("name", "asasasasa");
        setInitialValues(subject);
      });
    }
  }, [loadCurrentSubject, location]);

  return (
    <WaveContainer>
      <span className="goBack" onClick={() => navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <main className="create-container">
        <h1>Criar nova postagem</h1>
        <Formik
          innerRef={formikRef}
          initialValues={initialValues}
          validationSchema={NewContentSchema}
          enableReinitialize
          onSubmit={(values) => handleSubmit(values)}
        >
          {({ handleSubmit, handleChange, errors, touched, values }) => (
            <form className={step === 1 ? "form-1" : "form-2"}>
              <Dropzone onChange={imgHandler} preview={initialValues.image} />
              {errors.image && touched.image && (
                <span className="error text">{errors.image}</span>
              )}
              <input
                onChange={handleChange}
                type="text"
                name="name"
                value={values.name}
                placeholder="Título"
              />
              {errors.name && touched.name && (
                <span className="error text">{errors.name}</span>
              )}

              <select
                onChange={handleChange}
                name="grade"
                placeholder="Selecione uma série"
                value={values.grade || 0}
              >
                <option disabled hidden value="0">
                  Selecione uma opção
                </option>
                <option value="1º ano">1º ano</option>
                <option value="2º ano">2º ano</option>
                <option value="3º ano">3º ano</option>
              </select>
              {errors.grade && touched.grade && (
                <span className="error text">{errors.grade}</span>
              )}
              <div className="editor-wrapper">
                <CKEditor
                  editor={ClassicEditor}
                  style={{ width: "100%" }}
                  initData={
                    values.htmlContent || (
                      <p>
                        Este é o seu editor. Use-o para criar seus textos e
                        postagens!
                      </p>
                    )
                  }
                  onChange={inputHandler as any}
                />
              </div>
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
