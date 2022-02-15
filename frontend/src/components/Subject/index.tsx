import "./styles.scss";
import example from '../../assets/images/subject-example.png';
import { Link, useNavigate } from "react-router-dom";
import { ISubject } from "../../interfaces/subject";

interface Props{
  subject: ISubject;
  courseId: string;
}

export function Subject( {subject, courseId}: Props) {

  const navigate = useNavigate();

  return (
    <div onClick={()=> navigate(`/cursos/${courseId}/${subject.id}`) } className="card-container subject">
      <div className="card-header">
        <img src={example} alt="capa do card" />
        <Link to="#" >{subject.grade}</Link>
      </div>
      <div className="card-line"></div>
      <div className="subject-card-bottom">
        <span>{subject.position}-{subject.name}</span>
      </div>
    </div>
  );
}
