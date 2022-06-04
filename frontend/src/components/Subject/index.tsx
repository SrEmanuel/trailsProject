import "./styles.scss";
import { Link, useNavigate } from "react-router-dom";
import { ISubject } from "../../interfaces/subject";
import { FiCheckCircle, FiEdit, FiTrash2 } from "react-icons/fi";

interface Props {
  subject: ISubject;
  coursename: string;
  showOptions: boolean;
  topicId?: number;
  onDelete: (name: string) => void; 
}

export function Subject({ subject, coursename, showOptions, topicId, onDelete }: Props) {
  const navigate = useNavigate();

  return (
    <div className="card-container subject">
      <div
        className="card-header"
        onClick={() => navigate(`/cursos/${coursename}/${subject.linkName}`)}
      >
        { subject.completed && <FiCheckCircle color="var(--green)" size={24} /> }
        <img src={subject.imagePath} alt="capa do card" />
        <Link to="#">{subject.grade}</Link>
      </div>
      <div className="card-line"></div>
      <div className="subject-card-bottom">
        <span>
          {subject.position + 1}-{subject.name}
        </span>
        {showOptions && (
          <div>
            <FiEdit onClick={() => navigate(`/cursos/${coursename}/${topicId}/${subject.linkName}/atualizar`)} color="var(--purple)" size={24} />
            <FiTrash2
              onClick={() => onDelete(subject.linkName)}
              color="var(--red)"
              size={24}
            />
          </div>
        )}
      </div>
    </div>
  );
}
