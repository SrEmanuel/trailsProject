import "./styles.scss";
import { FiFileText } from "react-icons/fi";
import { ITrails } from "../../interfaces/Trail";
import { useNavigate } from "react-router-dom";

interface Props{
  color?: string;
  trail: ITrails;
}

export function Trail({color, trail}: Props) {

  const navigate = useNavigate();
   
  return (
    <div onClick={()=> navigate(`/cursos/${trail.linkName}`) }  className="card-container" style={ {background: color} } >
      <div className="card-header">
      <img src={trail.imagePath} alt="capa do card" />
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
