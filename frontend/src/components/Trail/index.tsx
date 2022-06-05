import "./styles.scss";
import { FiEdit, FiTrash2 } from "react-icons/fi";
import { ITrails } from "../../interfaces/Trail";
import { useNavigate } from "react-router-dom";
import { FormEvent } from "react";

interface Props {
  color?: string;
  trail: ITrails;
  mode: "teacher" | "student" | "guest" | "admin";
}

export function Trail({ color, trail, mode }: Props) {
  const navigate = useNavigate();

  const percent: number = trail.completedCount
    ? Math.round((trail.completedCount * 100) / trail.subjectsCount)
    : 0;

  function navigateToUpdateCourse(e: FormEvent) {
    e.stopPropagation();
    navigate(`/cursos/atualizar/${trail.linkName}`);
  }

  return (
    <div
      onClick={() => navigate(`/cursos/${trail.linkName}`)}
      className="trail-container"
      style={{ background: color }}
    >
      <img src={trail.imagePath} alt="capa do card" />
      <div className="card-line"></div>
      <div
        className={`trail-bottom ${
          (mode !== 'student' ) && "smaller-card-bottom"
        }`}
      >
        <div className="title-and-options">
          <h3>{trail.name}</h3>
          {mode === "admin" && (
            <div className="options">
              <FiEdit
                onClick={navigateToUpdateCourse}
                size={20}
                color="var(--grey)"
              />
              <FiTrash2 size={20} color="var(--grey)" />
            </div>
          )}
          {mode === "teacher" && (
            <div style={{ width: 24 }} className="options">
              <FiEdit
                onClick={navigateToUpdateCourse}
                size={20}
                color="var(--grey)"
              />
            </div>
          )}
        </div>
        {/*mode !== "teacher" && <span>Professor - João Fernandes de Souza</span>*/}
        {mode === "student" && (
          <>
            <div className="progress-bar">
              <div
                style={{ width: `${percent}%` }}
                className="current-progress"
              ></div>
            </div>
            <span className="progress-label">{percent}% concluido</span>
          </>
        )}
      </div>
    </div>
  );
}
