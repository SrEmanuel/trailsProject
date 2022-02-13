import "./styles.scss";

import exampleImage from "../../assets/images/subject.png";
import { FiFileText } from "react-icons/fi";

interface Props{
  color: string;
}

export function Trail(props: Props) {

  const image = null;    

  return (
    <div className="card-container" style={ {background: props.color} } >
      <div className="card-header">
       {image &&  <img src={exampleImage} alt="capa do card" />}
        <span>Matemática</span>
      </div>
      <div className="card-line"></div>
      <div className="card-bottom">
        <span>
          <FiFileText color="var(--purple)" size={24} />
          34 - artigos
        </span>
        <span>
          <FiFileText color="var(--red)" size={24} />
          34 - exercícios
        </span>
      </div>
    </div>
  );
}
