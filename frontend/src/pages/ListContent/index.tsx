import { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { NavBar } from "../../components/Navbar";
import { Paginator } from "../../components/Paginator";
import { Subject } from "../../components/Subject";
import { Trail } from "../../components/Trail";
import { Trails } from "../../interfaces/Trail";
import api from "../../services/api";
import "./styles.scss";

export function ListContent() {
  const [trails, setTrails] = useState<Trails[]>();

  const subjects = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
  const location = useLocation();

  useEffect(() => {
    async function handleLoadCourses() {
      const response = await api.get("/users/1/courses?size=1&page=0");
      setTrails(response.data);
    }
    handleLoadCourses()

  }, []);

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
          : subjects.map((subject) => <Subject />)}
      </div>
    </div>
  );
}
