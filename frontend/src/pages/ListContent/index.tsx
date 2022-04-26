import { Fragment, memo, useCallback, useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { DeleteSubject } from "../../components/DeleteSubject";
import { FloatingPlusButton } from "../../components/FloatingPlusButton";
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
import { AddNewSection } from "./components/addNewSection";
import "./styles.scss";

export const ListContent = memo(() => {
  const [trails, setTrails] = useState<ITrails[]>();
  const [topics, setTopics] = useState<ITopic[]>();
  const [currentCourse, setCurrentCourse] = useState<ITrails>();
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const { user, handleClearUserDataFromStorage, getIsTeacher } = useAuth();
  const [isTeacher, setIsTeacher] = useState<boolean>();
  const [addNewSection, setAddNewSection] = useState<boolean>(false);
  const [newSection, setNewSection] = useState<string>();
  const [isDeleteModalVisible, setIsDeleteModalVisible] = useState(false);
  const [selectedSubject, setSelectedSubject] = useState<string>("");
  const location = useLocation();

  const params = useParams();
  const navigate = useNavigate();

  const handleLoadCourses = useCallback(async () => {
    const url = (await getIsTeacher())
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
    const response = await api.get(`/courses/${params.coursename}/topics`);
    setTopics(response.data.content);
  }, [params.coursename]);

  const handleLoadSelectedCourse = useCallback(async () => {
    const response = await api.get(`/courses/${params.coursename}/`);
    setCurrentCourse(response.data);
  }, [params.coursename]);

  const handleUpdateTopics = useCallback(async () => {
    const topic = {
      name: newSection,
      position: (topics as any).length + 1,
      courseId: currentCourse?.id,
    };
    try {
      await api.post("/topics", topic);
      const response = await api.get(
        `/courses/${currentCourse?.linkName}/topics`
      );
      setTopics(response.data.content);
    } catch (error) {}
  }, [currentCourse?.id, currentCourse?.linkName, newSection, topics]);

  async function handleDeleteSubject(subjectLinkName: string) {
    setIsDeleteModalVisible(true);
    setSelectedSubject(subjectLinkName);
  }

  useEffect(() => {
    if (newSection) {
      handleUpdateTopics();
    }
  }, [currentCourse?.id, handleUpdateTopics, newSection, topics]);

  useEffect(() => {
    async function loadData() {
      setIsTeacher(await getIsTeacher());
      if (location.pathname === "/cursos") {
        await handleLoadCourses();
      } else {
        await handleLoadTopics();
        await handleLoadSelectedCourse();
      }
    }
    loadData();
  }, [
    location,
    isTeacher,
    user,
    handleLoadCourses,
    handleLoadTopics,
    handleLoadSelectedCourse,
    getIsTeacher,
  ]);

  return (
    <div className="container">
      <AddNewSection
        setIsVisible={setAddNewSection}
        setSection={setNewSection}
        isVisible={addNewSection}
      />
      <DeleteSubject
        isVisible={isDeleteModalVisible}
        setIsVisible={setIsDeleteModalVisible}
        selectedSubjectLinkName={selectedSubject}
      />
      <NavBar />
      <h1>
        {isTeacher
          ? `Bem vindo, ${user?.name}`
          : location.pathname === "/cursos"
          ? "Trilhas disponíveis"
          : currentCourse?.name}
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
                  coursename={params.coursename as string}
                  topicId={topic.id}
                  subject={subject}
                  onDelete={handleDeleteSubject}
                />
              ))}
              {isTeacher && (
                <PlusButton
                  text="Novo conteúdo"
                  color="var(--dark-green)"
                  topicId={topic.id}
                  coursename={params.coursename as unknown as number}
                />
              )}
            </div>

            {index === topics.length - 1 && isTeacher && (
              <FloatingPlusButton onClick={() => setAddNewSection(true)} />
            )}
          </Fragment>
        ))}
    </div>
  );
});
