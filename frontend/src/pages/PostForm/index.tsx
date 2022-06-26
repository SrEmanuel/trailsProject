import { useCallback, useEffect, useRef, useState } from "react";
import { FiArrowLeft, FiInfo } from "react-icons/fi";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { Dropzone } from "../../components/Dropzone";
import "./styles.scss";
import { Formik } from "formik";
import { NewContentSchema } from "../../schemas/newcontent.schema";
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

interface PostData {
  name: string;
  grade: string;
  image?: File | string;
  htmlContent?: string;
  imagePath?: string;
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

const names = [
  'Oliver Hansen',
  'Van Henry',
  'April Tucker',
  'Ralph Hubbard',
  'Omar Alexander',
  'Carlos Abbott',
  'Miriam Wagner',
  'Bradley Wilkerson',
  'Virginia Andrews',
  'Kelly Snyder',
];

export function PostForm() {
  const [initialValues, setInitialValues] = useState<PostData>();
  const [personName, setPersonName] = useState<string[]>([]);
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

  async function handleSubmit(values: PostData) {
    try {
      const image = values.image as File;
      delete values.image;
      const subject = { ...values, topicId: params.topicId, position: 2 };
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

  /*function inputHandler(event: any) {
    formikRef?.current?.setFieldValue("htmlContent", event.editor.getData());
  }*/

  function imgHandler(event: any) {
    formikRef?.current?.setFieldValue("image", event.target.files[0]);
  }

  const handleSelectionChange = (event: SelectChangeEvent<typeof personName>) => {
    const {
      target: { value },
    } = event;
    setPersonName(
      // On autofill we get a stringified value.
      typeof value === 'string' ? value.split(',') : value,
    );
  };

  useEffect(() => {
    if (location.pathname.includes("atualizar")) {
      loadCurrentSubject().then((subject) => {
        setInitialValues(subject);
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
        image: "",
        htmlContent: "",
      });
    }
  }, [loadCurrentSubject, location]);

  return (
    <div className="container">
      <span className="goBack" onClick={() => navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <main className="post-form-container">
        <h1>Formulário de postagem</h1>
        {initialValues && (
          <Formik
            innerRef={formikRef}
            initialValues={initialValues}
            validationSchema={NewContentSchema}
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
                  <span className="error text">{errors.name}</span>
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
                      value="1º ano"
                      control={<Radio />}
                      label="1º ano"
                    />
                    <FormControlLabel
                      value="2º ano"
                      control={<Radio />}
                      label="2º ano"
                    />
                    <FormControlLabel
                      value="3º ano"
                      control={<Radio />}
                      label="3º ano"
                    />
                    <FormControlLabel
                      value="outra"
                      control={<Radio />}
                      label="outra"
                    />
                  </RadioGroup>
                </FormControl>

                <span>Que tal adicionar uma imagem de capa?</span>

                <Dropzone
                  onChange={imgHandler}
                  preview={initialValues.imagePath}
                />
                {errors.image && touched.image && (
                  <span className="error text">{errors.image}</span>
                )}

                <span>
                  Este é o seu editor, sinta-se a vontade para adiconar textos e
                  mídias de sua preferência.
                </span>

                <CustomEditor />

                <span>Adicione exercicios ao final do conteúdo.</span>

                <span>Enunciado do exercicio:</span>

                <CustomEditor />

                <span>Alternativas</span>

                <FormControl className="alternatives-radio-selection">
                  <RadioGroup
                    name="grade"
                    value={values.grade}
                    onChange={handleChange}
                  >
                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="A"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <input placeholder="Adicione o texto da alternativa aqui..." />
                    </div>

                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="B"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <input placeholder="Adicione o texto da alternativa aqui..." />
                    </div>

                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="C"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <input placeholder="Adicione o texto da alternativa aqui..." />
                    </div>
                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="D"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <input placeholder="Adicione o texto da alternativa aqui..." />
                    </div>

                    <div className="radio-wrapper">
                      <FormControlLabel
                        value="E"
                        control={<Radio />}
                        label={<div></div>}
                      />
                      <input placeholder="Adicione o texto da alternativa aqui..." />
                    </div>
                  </RadioGroup>
                </FormControl>

                <span className="obs">
                  * Selecione a que será a resposta correta clicando sobre o
                  marcador circular.
                </span>

                <div className="row-wrapper">
                  <span>Competências BNCC:</span>
                  <FiInfo color="var(--grey)" size={24} />
                </div>

                <FormControl sx={{ m: 1, width: 300 }} className="custom-input">
                  <Select
                    multiple
                    value={personName}
                    onChange={handleSelectionChange}
                    input={<OutlinedInput />}
                    renderValue={(selected) => selected.join(", ")}
                    MenuProps={MenuProps}
                  >
                    {names.map((name) => (
                      <MenuItem key={name} value={name}>
                        <Checkbox checked={personName.indexOf(name) > -1} />
                        <ListItemText primary={name} />
                      </MenuItem>
                    ))}
                  </Select>
                </FormControl>

                <div className="buttons-container">
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
