import { useEffect, useState } from "react";
import { useLocation,  useParams } from "react-router-dom";
import { NavBar } from "../../components/Navbar";
import { Paginator } from "../../components/Paginator";
import { Trail } from "../../components/Trail";
import { Topic } from "../../interfaces/topic";
import { Trails } from "../../interfaces/Trail";
import api from "../../services/api";
import "./styles.scss";

export function ListContent() {
  const [trails, setTrails] = useState<Trails[]>();
  const [topics, setTopics] = useState<Topic[]>();

  const location = useLocation();
  const params = useParams();

  useEffect(() => {
    async function handleLoadCourses() {
      const response = await api.get("/courses");
      setTrails(response.data.content);
    }

    async function handleLoadTopics(){
      const response = await api.get(`/courses/${params.courseid}/topics`);
      setTopics(response.data);
    }

    location.pathname ==='/cursos' ? handleLoadCourses() : handleLoadTopics()

  }, [location, params]);

  return (
    <div className="container">
      <NavBar />
      <h1>
        {location.pathname === "/cursos"
          ? "Trilhas disponíveis"
          : "História Geral"}
      </h1>
      {location.pathname === "/cursos" && <Paginator />}
      <div className="trails-grid-container">
        {location.pathname === "/cursos"
          ? trails?.map((trail, index) => (
              <Trail
              trail={trail}
                color={
                  index % 3 === 0
                    ? "var(--green)"
                    : index % 3 === 1
                    ? "var(--red)"
                    : "var(--purple)"
                }
              />
            ))
          : topics?.map((topic) => (
            <>
            <h2>{topic.name}</h2>
            </>
          ))}
      </div>
    </div>
  );
}
