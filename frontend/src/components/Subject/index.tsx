import "./styles.scss";
import { Link, useNavigate } from "react-router-dom";
import { ISubject } from "../../interfaces/subject";
import { FiEdit, FiTrash2 } from "react-icons/fi";

interface Props {
  subject: ISubject;
  courseId: string;
  showOptions: boolean;
}

export function Subject({ subject, courseId, showOptions }: Props) {
  const navigate = useNavigate();

  return (
    <div
      onClick={() => navigate(`/cursos/${courseId}/${subject.id}`)}
      className="card-container subject"
    >
      <div className="card-header">
        <img src={subject.imagePath} alt="capa do card" />
        <Link to="#">{subject.grade}</Link>
      </div>
      <div className="card-line"></div>
      <div className="subject-card-bottom">
        <span>
          {subject.position}-{subject.name}
        </span>
        {showOptions && (
          <div>
            <FiEdit color="var(--purple)" size={24} />
            <FiTrash2 color="var(--red)" size={24} />
          </div>
        )}
      </div>
    </div>
  );
}
