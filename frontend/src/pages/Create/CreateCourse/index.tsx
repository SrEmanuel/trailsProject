import { Formik } from "formik";
import { useCallback, useEffect, useRef, useState } from "react";
import { FiArrowLeft } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { Dropzone } from "../../../components/Dropzone";
import { WaveContainer } from "../../../components/WaveContainer";
import { useAuth } from "../../../hooks/useAuth";
import { NewCourseSchema } from "../../../schemas/newcourse.schema";
import api from "../../../services/api";
import { handleNotifyError } from "../../../utils/handleNotifyError";
import "../styles.scss";

interface ITeacher{
  id: number;
  name: string;
  email: string;
}

interface ICourse{
  image: string;
  name: string;
  professorID: string;
}

export function CreateCourse() {
  const [teachers, setTeachers] = useState<ITeacher[]>();
  const navigate = useNavigate();
  const { getIsAdmin, handleClearUserDataFromStorage } = useAuth();
  const formikRef = useRef() as any;

  function imgHandler(event: any) {
    formikRef.current.setFieldValue("image", event.target.files[0]);
  }

  const handleLoadTeachers = useCallback(async() =>{
    try {
      const response = await api.get("/users/professors");
      setTeachers(response.data);
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }, [handleClearUserDataFromStorage, navigate])

  async function handleCreateCourse(course:ICourse){

    const formData = new FormData();
    formData.append('image', course.image)
    const courseData = { name: course.name, professorID: course.professorID };

    try {
      const response =  await api.post('/courses', courseData);

      await api.post(`/courses/${response.data.linkName}/add-image`, formData);
      toast.success('Curso criado com sucesso');
      navigate(-1);
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage)
    }
  }

  useEffect(() => {
    async function execute() {
      if (!(await getIsAdmin())) {
        toast.error("Você não possui permissão para criar uma turma");
        navigate("/");
      }
      await handleLoadTeachers();
    }

    execute();
  }, [getIsAdmin, handleLoadTeachers, navigate]);

  return (
    <WaveContainer>
      <span className="goBack" onClick={() => navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <main className="create-container">
        <h1>Criar novo curso</h1>
        <Formik
          innerRef={formikRef}
          initialValues={{ name: "", professorID: "", image: "" }}
          validationSchema={NewCourseSchema}
          onSubmit={(values) => handleCreateCourse(values)}
        >
          {({ handleChange, handleSubmit, errors, touched }) => (
            <form>
              <Dropzone onChange={imgHandler} />
              {errors.image && touched.image && (
                <span className="error text">{errors.image}</span>
              )}
              <input
                type="text"
                name="name"
                onChange={handleChange}
                placeholder="Nome do curso"
              />
              {errors.name && touched.name && (
                <span className="error text">{errors.name}</span>
              )}
              <select
                name="professorID"
                placeholder="Selecione uma série"
                onChange={handleChange}
                defaultValue="0"
              >
                <option disabled hidden value="0">
                  Selecione o professor da turma
                </option>
                {teachers?.map(teacher => ( <option key={teacher.id} value={teacher.id}>{teacher.name}</option>))}
              </select>
              {errors.professorID && touched.professorID && (
                <span className="error text">{errors.professorID}</span>
              )}
              <div className="buttons-container">
                <button type="button">Cancelar</button>
                <button type="submit" onClick={handleSubmit as any}>
                  Salvar
                </button>
              </div>
            </form>
          )}
        </Formik>
      </main>
    </WaveContainer>
  );
}
