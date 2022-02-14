import "./styles.scss";
import example from '../../assets/images/subject-example.png';
import { Link } from "react-router-dom";

export function Subject() {
  return (
    <div className="card-container subject">
      <div className="card-header">
        <img src={example} alt="capa do card" />
        <Link to="#" >1º ano</Link>
      </div>
      <div className="card-line"></div>
      <div className="subject-card-bottom">
        <span>1-Grécia antiga</span>
      </div>
    </div>
  );
}
