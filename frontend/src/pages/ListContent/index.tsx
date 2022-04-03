import {
  Fragment,
  memo,
  useCallback,
  useEffect,
  useState,
} from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import { NavBar } from "../../components/Navbar";
import { Paginator } from "../../components/Paginator";
import { PlusButton } from "../../components/PlusButton";
import { Subject } from "../../components/Subject";
import { Trail } from "../../components/Trail";
import { useAuth } from "../../hooks/useAuth";
import { ITopic } from "../../interfaces/topic";
import { ITrails } from "../../interfaces/Trail";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import "./styles.scss";

export const ListContent = memo(() => {
  const [trails, setTrails] = useState<ITrails[]>();
  const [topics, setTopics] = useState<ITopic[]>();
  const [courseName, setCourseName] = useState("");
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const { user, handleClearUserDataFromStorage, getIsTeacher } = useAuth();
  const [isTeacher, setIsTeacher] = useState<boolean>();
  const location = useLocation();

  const params = useParams();
  const navigate = useNavigate();

  const handleLoadCourses = useCallback(async () => {
    const url = await getIsTeacher()
      ? `/users/${user?.id}/courses?sort=name,asc`
      : `/courses?size=12&page=${page - 1}`;
    try {
      const response = await api.get(url);
      setTrails(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error: any) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }, [getIsTeacher, handleClearUserDataFromStorage, navigate, page, user?.id]);

  const handleLoadTopics = useCallback(async () => {
    const response = await api.get(`/courses/${params.courseid}/topics`);
    setTopics(response.data.content);
  }, [params.courseid]);

  const handleLoadSelectedCourse = useCallback(async () => {
    const response = await api.get(`/courses/${params.courseid}/`);
    setCourseName(response.data.name);
  }, [params.courseid]);

  useEffect(() => {
    async function loadData() {
      setIsTeacher( await getIsTeacher() );
      if (location.pathname === "/cursos") {
        await handleLoadCourses();
      } else {
        await handleLoadTopics();
        await handleLoadSelectedCourse();
      }
    }
      loadData();
  }, [location, isTeacher, user, handleLoadCourses, handleLoadTopics, handleLoadSelectedCourse, getIsTeacher]);

  return (
    <div className="container">
      <ToastContainer />
      <NavBar />
      <h1>
        {isTeacher
          ? `Bem vindo, ${user?.name}`
          : location.pathname === "/cursos"
          ? "Trilhas disponíveis"
          : courseName}
      </h1>
      {location.pathname === "/cursos" && (
        <Paginator page={page} totalPages={totalPages} setPage={setPage} />
      )}
      <div className="trails-grid-container">
        {location.pathname === "/cursos" &&
          trails?.map((trail) => <Trail key={trail.id} trail={trail} />)}
      </div>
      {location.pathname !== "/cursos" &&
        topics?.map((topic, index) => (
          <Fragment key={topic.id}>
            <h2 className="topic-title">{topic.name}</h2>
            <div className="trails-grid-container">
              {topic.subjects.map((subject) => (
                <Subject
                  showOptions={isTeacher ? true : false}
                  key={subject.id}
                  courseId={params.courseid as string}
                  subject={subject}
                />
              ))}
              {isTeacher && (
                <PlusButton
                  type="content"
                  text="Novo conteúdo"
                  color="var(--dark-green)"
                  topicId={topic.id}
                  courseId={params.courseId as unknown as number}
                />
              )}
            </div>

            {index === topics.length - 1 && isTeacher && (
              <PlusButton
                type="section"
                text="Nova sessão"
                color="var(--purple)"
                topicId={topic.id}
                courseId={params.courseid as unknown as number}
              />
            )}
          </Fragment>
        ))}
    </div>
  );
});
