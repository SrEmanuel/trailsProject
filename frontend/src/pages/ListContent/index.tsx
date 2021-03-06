import { memo, useCallback, useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { FloatingPlusButton } from "../../components/FloatingPlusButton";
import { NavBar } from "../../components/Navbar";
import { Paginator } from "../../components/Paginator";
import { Trail } from "../../components/Trail";
import { useAuth } from "../../hooks/useAuth";
import { ITopic } from "../../interfaces/topic";
import { ITrails } from "../../interfaces/Trail";
import api from "../../services/api";
import { handleNotifyError } from "../../utils/handleNotifyError";
import { AddOrUpdateSection } from "../../components/AddOrUpdateSection";

import { ReactComponent as AddNewContent } from "../../assets/images/AddNewContent.svg";
import "./styles.scss";
import { Topic } from "../../components/Topic";
import { DragDropTopicsList } from "./components/DragDropTopicsList";

export const ListContent = memo(() => {
  const [trails, setTrails] = useState<ITrails[]>();
  const [topics, setTopics] = useState<ITopic[]>();
  const [currentCourse, setCurrentCourse] = useState<ITrails>();
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const { user, handleClearUserDataFromStorage, getIsTeacher } = useAuth();
  const [isTeacher, setIsTeacher] = useState<boolean>();
  const [addOrUpdateSection, setAddOrUpdateSection] = useState<boolean>(false);
  const location = useLocation();
  const params = useParams();
  const navigate = useNavigate();
  const title = user
    ? `Bem vindo, ${user?.name}`
    : location.pathname === "/cursos"
    ? "Trilhas disponíveis"
    : currentCourse?.name;

  const handleLoadCourses = useCallback(async () => {
    const url = (await getIsTeacher())
      ? `/users/${user?.id}/courses?sort=name,asc`
      : `/courses`;
    try {
      const response = await api.get(url);
      setTrails(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (error: any) {
      handleNotifyError(error, navigate, handleClearUserDataFromStorage);
    }
  }, [getIsTeacher, handleClearUserDataFromStorage, navigate, page, user]);

  const handleLoadTopics = useCallback(async () => {
    const response = await api.get(`/courses/${params.coursename}/topics`);
    setTopics(response.data.content);
  }, [params.coursename]);

  const handleLoadSelectedCourse = useCallback(async () => {
    const response = await api.get(`/courses/${params.coursename}/`);
    setCurrentCourse(response.data);
  }, [params.coursename]);

  const loadData = useCallback(async () => {
    setIsTeacher(await getIsTeacher());
    if (location.pathname === "/cursos") {
      await handleLoadCourses();
    } else {
      await handleLoadTopics();
      await handleLoadSelectedCourse();
    }
  }, [
    getIsTeacher,
    handleLoadCourses,
    handleLoadSelectedCourse,
    handleLoadTopics,
    location.pathname,
  ]);

  useEffect(() => {
    loadData();
  }, [location, loadData]);

  return (
    <div className="container">
      <AddOrUpdateSection
        mode="create"
        setIsVisible={setAddOrUpdateSection}
        setTopics={setTopics}
        currentCourse={currentCourse as ITrails}
        isVisible={addOrUpdateSection}
      />
      <NavBar />
      <h1>{title}</h1>
      {location.pathname === "/cursos" && (
        <Paginator page={page} totalPages={totalPages} setPage={setPage} />
      )}
      <div className="trails-grid-container">
        {location.pathname === "/cursos" &&
          trails?.map((trail) => (
            <Trail
              mode={
                user?.roles.includes("ADMIN")
                  ? "admin"
                  : isTeacher
                  ? "teacher"
                  : user
                  ? "student"
                  : "guest"
              }
              onChange={loadData}
              key={trail.id}
              trail={trail}
            />
          ))}
      </div>

      {location.pathname !== "/cursos" &&
        !isTeacher &&
        topics?.map((topic) => (
          <Topic
            key={topic.id}
            topic={topic}
            params={params}
            enableAdminMode={false}
            onChange={loadData}
          />
        ))}

      {location.pathname !== "/cursos" && isTeacher && (
        <>
          <DragDropTopicsList
            setTopics={setTopics}
            topics={topics as ITopic[]}
            params={params}
            onContentChange={loadData}
          />
          <FloatingPlusButton onClick={() => setAddOrUpdateSection(true)} />
        </>
      )}

      {(topics === undefined || (topics && topics.length === 0)) &&
        isTeacher &&
        location.pathname !== "/cursos" && (
          <div className="empty-course-list">
            <h2>Clique para adicionar sua primeira sessão de conteúdos</h2>
            <AddNewContent onClick={() => setAddOrUpdateSection(true)} />
          </div>
        )}
    </div>
  );
});
