import "./styles.scss";

import exampleImage from "../../assets/images/subject.png";
import { FiFileText } from "react-icons/fi";

export function Trail() {
  return (
    <div className="card-container">
      <div className="card-header">
        <img src={exampleImage} alt="capa do card" />
        <span>Matemática</span>
      </div>
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
