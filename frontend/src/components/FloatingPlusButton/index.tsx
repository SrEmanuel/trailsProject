import { FiPlus } from 'react-icons/fi';
import './styles.scss';

export function FloatingPlusButton(){
  return(
    <button className="floating-btn">
      <FiPlus size={24} color="var(--white)" />
    </button>
  )
}