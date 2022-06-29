import { useCallback, useEffect, useRef, useState } from "react";
import { FiArrowLeft, FiInfo } from "react-icons/fi";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Dropzone } from "../../components/Dropzone";
import "./styles.scss";
import { Formik } from "formik";
import api from "../../services/api";
import { toast } from "react-toastify";
import { useAuth } from "../../hooks/useAuth";
import { handleNotifyError } from "../../utils/handleNotifyError";
import {
  Checkbox,
  FormControl,
  FormControlLabel,
  ListItemText,
  MenuItem,
  Radio,
  RadioGroup,
  Select,
  SelectChangeEvent,
  OutlinedInput,
} from "@mui/material";
import { CustomEditor } from "../../components/CustomEditor";
import { PostSchema } from "../../schemas/post.schema";
import { ICompetence } from "../../interfaces/competence";

interface PostData {
  name: string;
  grade: string;
  image?: File | string;
  htmlContent?: string;
  imagePath?: string;
  QuestionHtmlContent: string;
  correct: string;
  answerA: string;
  answerB: string;
  answerC: string;
  answerD: string;
  answerE: string;
  id?: string;
}

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

export function PostForm() {
  const [initialValues, setInitialValues] = useState<PostData>();
  const [competences, setCompetences] = useState<ICompetence[]>();
  const [selectedCompetences, setSelectedCompetences] = useState<string[]>([]);
  const navigate = useNavigate();
  const { handleClearUserDataFromStorage } = useAuth();
  const formikRef = useRef() as any;
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

  const loadCompetences = useCallback(async () => {
    try {
      const response = await api.get("/competences");
      console.log(response);
      setCompetences(response.data.content);
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }, [handleClearUserDataFromStorage, navigate]);

  async function handleSubmit(values: PostData) {
    try {
      const image = values.image as File;
      delete values.image;

      const competencesPayload = competences
        ?.filter((competence) => selectedCompetences.includes(competence.name))
        .map((competence) => ({
          id: competence.id,
          operation: location.pathname.includes("atualizar")
            ? "ADD"
            : "CREATE",
        }));
      
      const subject = {
        name: values.name,
        grade: values.grade,
        htmlContent: values.htmlContent,
        topicId: params.topicid,
        position: 2,
        questions: [
          {
            htmlContent: values.QuestionHtmlContent,
            operation: location.pathname.includes("atualizar")
              ? "UPDATE"
              : "CREATE",
            answerA: values.answerA,
            answerB: values.answerB,
            answerC: values.answerC,
            answerD: values.answerD,
            answerE: values.answerE,
            correct: values.correct,
            id: values.id,
            competences: competencesPayload
          },
        ],
      };
      const data = new FormData();
      data.append("image", image);

      const response = location.pathname.includes("atualizar")
        ? await api.put(`/subjects/${params.contentname}`, subject)
        : await api.post("/subjects", subject);
      const subjectLinkName = response.data.linkName;
      await api.post(`/subjects/${subjectLinkName}/add-image`, data);
      toast.success(
        `Conteúdo ${
          location.pathname.includes("atualizar") ? "atualizado" : "criado"
        } com sucesso!`
      );
      navigate(-1);
    } catch (error: any) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }

  function postHtmlContentInputHandler(value: string) {
    formikRef?.current?.setFieldValue("htmlContent", value);
  }

  function QuestionHtmlContentInputHandler(value: string) {
    formikRef?.current?.setFieldValue("QuestionHtmlContent", value);
  }

  function imgHandler(event: any) {
    formikRef?.current?.setFieldValue("image", event.target.files[0]);
  }

  const handleSelectionChange = (
    event: SelectChangeEvent<typeof selectedCompetences>
  ) => {
    const {
      target: { value },
    } = event;
    setSelectedCompetences(
      // On autofill we get a stringified value.
      typeof value === "string" ? value.split(",") : value
    );
  };

  useEffect(() => {
    if (location.pathname.includes("atualizar")) {
      loadCurrentSubject().then((subject) => {
        setInitialValues({
          htmlContent: subject.htmlContent,
          imagePath: subject.imagePath,
          name: subject.name,
          grade: subject.grade,
          QuestionHtmlContent: subject.questions[0].htmlContent,
          correct: subject.questions[0].correct,
          answerA: subject.questions[0].answerA,
          answerB: subject.questions[0].answerB,
          answerC: subject.questions[0].answerC,
          answerD: subject.questions[0].answerD,
          answerE: subject.questions[0].answerE,
          id: subject.questions[0].id,
        });
        fetch(subject.imagePath).then(async (response) => {
          const blob = await response.blob();
          const file = new File(
            [blob],
            subject.imagePath.split("/uploads/")[1]
          );
          formikRef.current?.setFieldValue("image", file);
        });
      });
    } else {
      setInitialValues({
        grade: "",
        name: "",
        imagePath: "",
        htmlContent: "",
        QuestionHtmlContent: "",
        correct: "",
        answerA: "",
        answerB: "",
        answerC: "",
        answerD: "",
        answerE: "",
      });
    }

    loadCompetences();
  }, [loadCompetences, loadCurrentSubject, location]);

  return (
    <div className="container" id="post-form-container">
      <span className="goBack" onClick={() => navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <main className="post-form">
        <h1>Formulário de postagem</h1>
        {initialValues && (
          <Formik
            innerRef={formikRef}
            initialValues={initialValues}
            validationSchema={PostSchema}
            enableReinitialize
            onSubmit={(values) => handleSubmit(values)}
          >
            {({ handleSubmit, handleChange, errors, touched, values }) => (
              <form>
                <input
                  onChange={handleChange}
                  type="text"
                  name="name"
                  value={values.name}
                  placeholder="Nome da sua postagem..."
                />
                {errors.name && touched.name && (
                  <p className="error text">{errors.name}</p>
                )}

                <span>Para qual série é recomendada?</span>

                <FormControl className="radio-selection">
                  <RadioGroup
                    row
                    name="grade"
                    value={values.grade}
                    onChange={handleChange}
                  >
                    <FormControlLabel
                      value="1º Ano"
                      control={<Radio />}
                      label="1º Ano"
                    />
                    <FormControlLabel
                      value="2º Ano"
                      control={<Radio />}
                      label="2º Ano"
                    />
                    <FormControlLabel
                      value="3º Ano"
                      control={<Radio />}
                      label="3º Ano"
                    />
                    <FormControlLabel
                      value="outra"
                      control={<Radio />}
                      label="outra"
                    />
                  </RadioGroup>
                </FormControl>

                {errors.grade && touched.grade && (
                  <p className="error text">{errors.grade}</p>
                )}

                <span>Que tal adicionar uma imagem de capa?</span>

                <Dropzone
                  onChange={imgHandler}
                  preview={initialValues.imagePath}
                />
                {errors.image && touched.image && (
                  <p className="error text">{errors.image}</p>
                )}

                <span>
                  Este é o seu editor, sinta-se a vontade para adiconar textos e
                  mídias de sua preferência.
                </span>

                <CustomEditor
                  initialValue={values.htmlContent}
                  onChange={postHtmlContentInputHandler}
                />
                {errors.htmlContent && touched.htmlContent && (
                  <p className="error text">{errors.htmlContent}</p>
                )}

                <span>
                  Adicione um exercico ao final do conteúdo (opcional) .
                </span>

                <span>Enunciado do exercicio:</span>

                <CustomEditor
                  initialValue={values.QuestionHtmlContent}
                  onChange={QuestionHtmlContentInputHandler}
                />
                {errors.QuestionHtmlContent && touched.QuestionHtmlContent && (
                  <p className="error text">{errors.QuestionHtmlContent}</p>
                )}

                <span>Alternativas</span>

                <FormControl className="alternatives-radio-selection">
                  <RadioGroup
                    name="correct"
                    value={values.correct}
                    onChange={handleChange}
                  >
                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="A"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <div className="column-wrapper">
                        <input
                          value={values.answerA}
                          name="answerA"
                          onChange={handleChange}
                          placeholder="Adicione o texto da alternativa aqui..."
                        />
                        {errors.answerA && touched.answerA && (
                          <p className="error text">{errors.answerA}</p>
                        )}
                      </div>
                    </div>

                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="B"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <div className="column-wrapper">
                        <input
                          value={values.answerB}
                          name="answerB"
                          onChange={handleChange}
                          placeholder="Adicione o texto da alternativa aqui..."
                        />
                        {errors.answerB && touched.answerB && (
                          <p className="error text">{errors.answerB}</p>
                        )}
                      </div>
                    </div>

                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="C"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <div className="column-wrapper">
                        <input
                          value={values.answerC}
                          name="answerC"
                          onChange={handleChange}
                          placeholder="Adicione o texto da alternativa aqui..."
                        />
                        {errors.answerC && touched.answerC && (
                          <p className="error text">{errors.answerC}</p>
                        )}
                      </div>
                    </div>
                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="D"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <div className="column-wrapper">
                        <input
                          value={values.answerD}
                          name="answerD"
                          onChange={handleChange}
                          placeholder="Adicione o texto da alternativa aqui..."
                        />
                        {errors.answerD && touched.answerD && (
                          <p className="error text">{errors.answerD}</p>
                        )}
                      </div>
                    </div>

                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="E"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <div className="column-wrapper">
                        <input
                          value={values.answerE}
                          name="answerE"
                          onChange={handleChange}
                          placeholder="Adicione o texto da alternativa aqui..."
                        />
                        {errors.answerE && touched.answerE && (
                          <p className="error text">{errors.answerE}</p>
                        )}
                      </div>
                    </div>
                  </RadioGroup>
                </FormControl>

                <span className="obs">
                  * Selecione a que será a resposta correta clicando sobre o
                  marcador circular.
                </span>

                <div className="row-wrapper">
                  <span>Competências BNCC:</span>
                  <FiInfo color="var(--grey)" size={30} />
                </div>

                <FormControl sx={{ m: 1, width: 300 }} className="custom-input">
                  <Select
                    multiple
                    value={selectedCompetences}
                    onChange={handleSelectionChange}
                    input={<OutlinedInput />}
                    renderValue={(selected) => selected.join(", ")}
                    MenuProps={MenuProps}
                  >
                    {competences?.map((competence) => (
                      <MenuItem key={competence.id} value={competence.name}>
                        <Checkbox
                          checked={selectedCompetences.includes(
                            competence.name
                          )}
                        />
                        <ListItemText primary={competence.name} />
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

                <div className="buttons-container btn-container">
                  <button type="button" onClick={() => navigate(-1)}>
                    Cancelar
                  </button>
                  <button type="button" onClick={() => handleSubmit()}>
                    Enviar
                  </button>
                </div>
              </form>
            )}
          </Formik>
        )}
      </main>
    </div>
  );
}
