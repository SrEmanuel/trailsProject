import { FiPlusCircle } from "react-icons/fi";
import { useNavigate } from "react-router-dom";

import './styles.scss';

interface Props {
  text: string;
  color: string;
  topicId: number;
  coursename: number;
}

export function PlusButton({ text, color, coursename, topicId }: Props) {
  const navigate = useNavigate();

  function handleNavigateToCreatePage(){
    navigate(`/cursos/${coursename}/${topicId}/novo-conteudo`);
  }

  return (
    <button onClick={ () => handleNavigateToCreatePage()  } className="plus-btn" style={{color: color}} >
      <FiPlusCircle size={24} color={color} />
      {text}
    </button>
  );
}
