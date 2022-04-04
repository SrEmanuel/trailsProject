import { FiPlusCircle } from "react-icons/fi";
import { useNavigate } from "react-router-dom";

import './styles.scss';

interface Props {
  text: string;
  color: string;
  type: 'content' | 'section';
  topicId: number;
  coursename: number;
}

export function PlusButton({ text, color, type, coursename, topicId }: Props) {
  const navigate = useNavigate();

  function handleNavigateToCreatePage(){
    type === 'content'? navigate(`/cursos/${coursename}/${topicId}/novo-conteudo`) : navigate('/novo-curso')
  }

  return (
    <button onClick={ () => handleNavigateToCreatePage()  } className="plus-btn" style={{color: color}} >
      <FiPlusCircle size={24} color={color} />
      {text}
    </button>
  );
}
