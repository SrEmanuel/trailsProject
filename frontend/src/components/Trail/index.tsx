import "./styles.scss";

import exampleImage from "../../assets/images/subject.png";
import { FiFileText } from "react-icons/fi";
import { Trails } from "../../interfaces/Trail";
import { useNavigate } from "react-router-dom";

interface Props{
  color: string;
  trail: Trails;
}

export function Trail({color, trail}: Props) {

  const navigate = useNavigate();
   
  return (
    <div onClick={()=> navigate(`/cursos/${trail.id}`) }  className="card-container" style={ {background: color} } >
      <div className="card-header">
       { !trail.image &&  <img src={exampleImage} alt="capa do card" />}
        <span>{trail.name}</span>
      </div>
      <div className="card-line"></div>
      <div className="card-bottom">
        <span>
          <FiFileText color="var(--purple)" size={24} />
          {trail.subjectsCount} - artigos
        </span>
        <span>
          <FiFileText color="var(--red)" size={24} />
          34 - exercícios
        </span>
      </div>
    </div>
  );
}
