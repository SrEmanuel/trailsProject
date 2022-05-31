import "./styles.scss";
import { FiEdit, FiTrash2 } from "react-icons/fi";
import { ITrails } from "../../interfaces/Trail";
import { useNavigate } from "react-router-dom";

interface Props {
  color?: string;
  trail: ITrails;
  mode: "teacher" | "student" | "guest" | "admin";
}

export function Trail({ color, trail, mode }: Props) {
  const navigate = useNavigate();

  const percent: number = trail.completedCount
    ? (trail.completedCount * 100) / trail.subjectsCount
    : 0;

  return (
    <div
      onClick={() => navigate(`/cursos/${trail.linkName}`)}
      className="trail-container"
      style={{ background: color }}
    >
      <img src={trail.imagePath} alt="capa do card" />
      <div className="card-line"></div>
      <div className="trail-bottom">
        <div className="title-and-options">
          <h3>{trail.name}</h3>
          {(mode === "admin" || mode === "teacher") && (
            <div className="options">
              <FiEdit size={20} color="var(--grey)" />
              <FiTrash2 size={20} color="var(--grey)" />
            </div>
          )}
        </div>
        {mode !== "teacher" && <span>Professor - João Fernandes de Souza</span>}
        {mode === "student" && (
          <>
            <div className="progress-bar">
              <div style={{ width: `${percent}%` }} className="current-progress"></div>
            </div>
            <span className="progress-label">{percent}% concluido</span>
          </>
        )}
      </div>
    </div>
  );
}
