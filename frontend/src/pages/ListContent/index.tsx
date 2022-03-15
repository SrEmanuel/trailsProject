import { Fragment, useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { NavBar } from "../../components/Navbar";
import { Paginator } from "../../components/Paginator";
import { Subject } from "../../components/Subject";
import { Trail } from "../../components/Trail";
import { useAuth } from "../../hooks/useAuth";
import { ITopic } from "../../interfaces/topic";
import { ITrails } from "../../interfaces/Trail";
import api from "../../services/api";
import "./styles.scss";

export function ListContent() {
  const [trails, setTrails] = useState<ITrails[]>();
  const [topics, setTopics] = useState<ITopic[]>();
  const [courseName, setCourseName] = useState("");
  const [page, setPage] = useState(1)
  const [totalPages, setTotalPages] = useState(1)

  const location = useLocation();
  const params = useParams();
  const { user } = useAuth();

  useEffect(() => {
    async function handleLoadCourses() {
      const response = await api.get(`/courses?size=12&page=${page-1}`);
      setTrails(response.data.content);
      setTotalPages(response.data.totalPages);
    }

    async function handleLoadTopics() {
      const response = await api.get(`/courses/${params.courseid}/topics`);
      setTopics(response.data.content);
    }

    async function handleLoadSelectedCourse() {
      const response = await api.get(`/courses/${params.courseid}/`);
      setCourseName(response.data.name);
    }

    if (location.pathname === "/cursos") {
      handleLoadCourses();
    } else {
      handleLoadTopics();
      handleLoadSelectedCourse();
    }
  }, [location, params, page]);

  return (
    <div className="container">
      <NavBar />
      <h1>
        { user?.authorities.find(item => item.authority.includes('PROFESSOR')) ? `Bem vindo, ${user.name}` : location.pathname === "/cursos" ? "Trilhas disponíveis" : courseName}
      </h1>
      {location.pathname === "/cursos" && <Paginator page={page} totalPages={totalPages} setPage={setPage} />}
      <div className="trails-grid-container">
        {location.pathname === "/cursos" &&
          trails?.map((trail, index) => (
            <Trail
              key={trail.id}
              trail={trail}
            />
          ))}
      </div>
      {location.pathname !== "/cursos" &&
        topics?.map((topic) => (
          <Fragment key={topic.id}>
            <h2 className="topic-title">{topic.name}</h2>
            <div className="trails-grid-container">
              {topic.subjects.map((subject) => (
                <Subject
                  key={subject.id}
                  courseId={params.courseid as string}
                  subject={subject}
                />
              ))}
            </div>
          </Fragment>
        ))}
    </div>
  );
}
