import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
import { NavBar } from "../../components/Navbar";
import { Paginator } from "../../components/Paginator";
import { Subject } from "../../components/Subject";
import { Trail } from "../../components/Trail";
import { ITopic } from "../../interfaces/topic";
import { ITrails } from "../../interfaces/Trail";
import api from "../../services/api";
import "./styles.scss";

export function ListContent() {
  const [trails, setTrails] = useState<ITrails[]>();
  const [topics, setTopics] = useState<ITopic[]>();
  const [courseName, setCourseName] = useState("");

  const location = useLocation();
  const params = useParams();

  useEffect(() => {
    async function handleLoadCourses() {
      const response = await api.get("/courses");
      setTrails(response.data.content);
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
  }, [location, params]);

  return (
    <div className="container">
      <NavBar />
      <h1>
        {location.pathname === "/cursos" ? "Trilhas disponíveis" : courseName}
      </h1>
      {location.pathname === "/cursos" && <Paginator />}
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
          <>
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
          </>
        ))}
    </div>
  );
}
