import { Formik } from "formik";
import { ChangeEvent, useCallback, useEffect, useRef, useState } from "react";
import { FiArrowLeft } from "react-icons/fi";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { toast } from "react-toastify";
import Chip from "@mui/material/Chip";
import Stack from "@mui/material/Stack";
import Avatar from "@mui/material/Avatar";
import { Dropzone } from "../../../components/Dropzone";
import { WaveContainer } from "../../../components/WaveContainer";
import { useAuth } from "../../../hooks/useAuth";
import { ITrails } from "../../../interfaces/Trail";
import {
  NewCourseSchema,
  UpdateCourseSchema,
} from "../../../schemas/newcourse.schema";
import api from "../../../services/api";
import { handleNotifyError } from "../../../utils/handleNotifyError";
import "../styles.scss";

interface ITeacher {
  id: string;
  name: string;
  email: string;
}

interface ICourse {
  image: string;
  name: string;
  professors?: ITeacher[];
}

export function CreateCourse() {
  const { getIsAdmin, handleClearUserDataFromStorage, user } = useAuth();
  const [initialValues, setInitialValues] = useState<ICourse>();
  const [teachers, setTeachers] = useState<ITeacher[]>();
  const [selectedTeachers, setSelectedTeachers] = useState<ITeacher[]>([]);
  const params = useParams();
  const navigate = useNavigate();
  const location = useLocation();
  const formikRef = useRef() as any;

  function imgHandler(event: any) {
    formikRef.current.setFieldValue("image", event.target.files[0]);
  }

  function handleAddTeacher(e: ChangeEvent<HTMLSelectElement>) {
    const teacher =
      teachers && teachers[(e.target.value as unknown as number) - 1];
    formikRef.current.setFieldValue("professors", [
      ...selectedTeachers,
      teacher,
    ]);
    teacher && setSelectedTeachers([...selectedTeachers, teacher]);
  }

  function handleRemoveTeacher(id: string) {
    formikRef.current.setFieldValue(
      "professors",
      selectedTeachers.filter((t) => t.id !== id)
    );
    setSelectedTeachers(selectedTeachers.filter((t) => t.id !== id));
  }

  const handleLoadUsers = useCallback(async () => {
    try {
      const response = await api.get("/users/");
      setTeachers(response.data);
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }, [handleClearUserDataFromStorage, navigate]);

  const handleLoadCurrentCourseData: () => Promise<ITrails> =
    useCallback(async () => {
      try {
        const response = await api.get(`/courses/${params.coursename}`);
        return response.data;
      } catch (error) {
        handleNotifyError(error, navigate, handleClearUserDataFromStorage);
      }
    }, [handleClearUserDataFromStorage, navigate, params]);

  const handleCreateCourse = async (course: ICourse) => {
    const formData = new FormData();
    formData.append("image", course.image);
    const courseData = { name: course.name, professors: course.professors };

    try {
      const response = location.pathname.includes("atualizar")
        ? await api.put(`/courses/${params.coursename}`, courseData)
        : await api.post("/courses", courseData);

      await api.post(`/courses/${response.data.linkName}/add-image`, formData);
      toast.success("Enviado com sucesso!");
      navigate(-1);
    } catch (error) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  };

  useEffect(() => {
    async function execute() {
      if (!(await getIsAdmin()) && location.pathname.includes("novo-curso")) {
        toast.error("Você não possui permissão para criar uma turma");
        navigate("/");
      } else if (await getIsAdmin()) {
        await handleLoadUsers();
      }
    }

    execute();

    if (location.pathname.includes("atualizar")) {
      handleLoadCurrentCourseData().then((course) => {
        setInitialValues({
          name: course.name,
          image: course.imagePath,
          professors: course.professors,
        });

        setSelectedTeachers(course.professors);

        fetch(course.imagePath).then(async (response) => {
          const blob = await response.blob();
          const file = new File([blob], course.imagePath.split("/uploads/")[1]);
          formikRef.current?.setFieldValue("image", file);
        });
      });
    } else {
      setInitialValues({
        name: "",
        image: "",
        professors: [],
      });
    }
  }, [
    getIsAdmin,
    handleLoadCurrentCourseData,
    handleLoadUsers,
    location,
    navigate,
  ]);

  return (
    <WaveContainer>
      <span className="goBack" onClick={() => navigate(-1)}>
        <FiArrowLeft size={24} color="var(--dark-green)" /> Voltar
      </span>
      <main className="create-container">
        <h1>
          {location.pathname === "novo-curso"
            ? "Criar novo curso"
            : "Atualizar curso"}
        </h1>
        {initialValues && (
          <Formik
            innerRef={formikRef}
            initialValues={initialValues}
            validationSchema={
              location.pathname.includes("atualizar")
                ? UpdateCourseSchema
                : NewCourseSchema
            }
            onSubmit={(values) => handleCreateCourse(values)}
          >
            {({ handleChange, handleSubmit, errors, touched, values }) => (
              <form>
                <Dropzone
                  preview={initialValues?.image}
                  onChange={imgHandler}
                />
                {errors.image && touched.image && (
                  <span className="error text">{errors.image}</span>
                )}
                <input
                  type="text"
                  name="name"
                  value={values.name}
                  onChange={handleChange}
                  placeholder="Nome do curso"
                />
                {errors.name && touched.name && (
                  <span className="error text">{errors.name}</span>
                )}
                {user?.roles.includes("ADMIN") && (
                  <>
                    <select onChange={handleAddTeacher} value={0}>
                      <option disabled hidden value="0">
                        Adicionar professor...
                      </option>
                      {teachers?.map((teacher) => (
                        <option key={teacher.id} value={teacher.id}>
                          {teacher.name}
                        </option>
                      ))}
                    </select>
                    <Stack className="chip-stack" direction="row" spacing={1}>
                      {selectedTeachers.map((teacher) => (
                        <Chip
                          key={teacher.id}
                          className="chip"
                          onDelete={() => handleRemoveTeacher(teacher.id)}
                          onChange={handleChange("professorID")}
                          color="success"
                          avatar={
                            <Avatar
                              alt={teacher.name}
                              src="/static/images/avatar/1.jpg"
                            />
                          }
                          label={teacher.name}
                        />
                      ))}
                    </Stack>
                  </>
                )}
                {errors.professors && touched.professors && (
                  <span className="error text">{errors.professors}</span>
                )}
                <div className="buttons-container">
                  <button onClick={() => navigate(-1)} type="button">
                    Cancelar
                  </button>
                  <button type="submit" onClick={handleSubmit as any}>
                    Salvar
                  </button>
                </div>
              </form>
            )}
          </Formik>
        )}
      </main>
    </WaveContainer>
  );
}
